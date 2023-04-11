# Gallery App
Simple app that displays images from the user's phone gallery, implemented for student's project, providing infinite scrolling and the possibility to change the number of columns.

## How to use
1. When the app starts, it requests permission to access the phone's gallery.
2. The app loads the images from the gallery, and displays them in a grid, with 3 columns.
3. As the user scrolls down, more images are loaded, providing infinite scrolling.
4. The user can change the number of columns in the grid by using the "Increase columns" and "Decrease columns" buttons at the bottom of the screen.
5. The user can tap on an image to open it in full screen mode, and swipe left or right to navigate through the images.

## Technical details
The app is written in Kotlin, and uses the following libraries:

- Glide for image loading and caching
- Gson for JSON serialization and deserialization

### Packages
The app consists of two packages:
#### pl.maty.galleryapp.activity
Contains the MainActivity class, which is the main activity of the app. It requests permission to access the phone's gallery, loads the images, and displays them in a grid using a RecyclerView. The RecyclerView provides infinite scrolling by loading more images as the user scrolls down.

The MainActivity also provides the option to change the number of columns in the grid, by using the "Increase columns" and "Decrease columns" buttons.

#### pl.maty.galleryapp.adapter
Contains the Image class, which represents an image, and the ImageAdapter class, which is a RecyclerView.Adapter that binds the Image objects to the RecyclerView.

The ImageAdapter also handles the click events on the images, and opens the images in full screen mode using the FullScreenActivity class.

## Permissions
The app requests the READ_EXTERNAL_STORAGE permission to access the phone's gallery.

## Future improvements
Add search functionality to search for specific images.
Add support for videos.
Add the ability to sort the images by date, name, or size.


## Demo
https://user-images.githubusercontent.com/72618993/231239350-dee10cb5-1a9d-4c4c-98de-78e4169aa746.mp4

