package pl.maty.galleryapp.activity

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pl.maty.galleryapp.R
import pl.maty.galleryapp.adapter.FullSizeAdapter
import pl.maty.galleryapp.adapter.Image
import java.lang.reflect.Type

class FullScreenActivity() : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var imagesList: ArrayList<Image>? = null
    var position: Int? = null
    var imagesCounter = 0

    var allImagesUri: Uri? = null
    var projectionArray: Array<String>? = null
    var cursorMain: Cursor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        if (savedInstanceState == null) {
            val i: Intent = intent
            imagesList = GsonBuilder().create().fromJson(
                i.getStringExtra("imagesListJSON"),
                object : TypeToken<ArrayList<Image>>() {}.type as Type
            ) as ArrayList<Image>
            position = i.getIntExtra("position", 0)
        }

        setTitle("Name: " + imagesList?.get(position!!)?.imageName)

        viewPager = findViewById(R.id.viewPager)
        viewPager?.adapter = FullSizeAdapter(this, imagesList!!)
        viewPager?.setCurrentItem(position!!, true)

        allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        projectionArray = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )
        cursorMain = this@FullScreenActivity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projectionArray,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        initScrollListener()
    }

    private fun initScrollListener() {
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                setTitle("Name: " + imagesList?.get(position!!)?.imageName)
                if (position == (imagesList?.size!! - 1)) {
                    println("last page")
                    imagesCounter = imagesList?.size!!
                    loadImagesPackage()
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun loadImagesPackage() {
        println("call function loadImagesPackage()")
        Toast.makeText(this@FullScreenActivity, "Loading", Toast.LENGTH_SHORT).show()
        var i = 0
        val prevCounter = imagesList?.size
        try {
            if (imagesCounter == 0) {
                cursorMain!!.moveToFirst()
            } else {
                cursorMain!!.moveToPosition(imagesCounter)
            }
            while (cursorMain!!.moveToNext() && i < 10) {
                val image = Image()
                image.imagePath =
                    cursorMain?.getString(cursorMain!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName =
                    cursorMain?.getString(cursorMain!!.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                imagesList?.add(image)
                viewPager?.adapter?.notifyDataSetChanged()
                i++
            }
            imagesCounter += i
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //cursorMain!!.close()
        }
        println("loaded images package: " + (imagesList?.size?.minus(prevCounter!!)) + ", total images: " + imagesList?.size + " / "  + cursorMain?.count)

    }
}


