# QR and Barcode Scanner Android Application

## Description

The Android app, for QR and Barcode scanning is a tool that makes it easy to scan QR codes and barcodes using the camera on your device. This amazing app quickly captures types of codes. Converts them into useful information. It also seamlessly connects with a Firebase Real time Database allowing users to effortlessly store and organize their data. This creates a system, for keeping track of items and their relevant details. Whether you're using it personally or for an organization this app provides a solution to improve the tracking and accessibility of data.


## Features

- Scan QR codes and barcodes using the device's camera.
- Store scanned data in a Firebase real-time database.
- Check for existing data in the database before storing.
- User-friendly interface with easy-to-use controls.
- Real-time updates for stored data.

## Technologies Used

- Android Studio: The IDE for Android app development.
- Kotlin: The programming language used for developing the app.
- Firebase Realtime Database: Used to store and manage scanned data.
- Google Mobile Vision API: Enables barcode scanning using the device's camera.

## Dependencies

The app uses the following dependencies:

- Google Mobile Vision API:
  ```gradle-Kotlin
  implementation "com.google.android.gms:play-services-vision:20.1.3"

- Firebase Realtime Database:
  ``` Firebase-connectivity
   implementation "com.google.firebase:firebase-database-ktx:20.2.2"


## How to Use

1. Clone or download the repository.

2. Open the project in Android Studio.

3. Configure your Firebase project:
   - Create a new Firebase project or use an existing one.
   - Add the Android app to your Firebase project and follow the setup instructions to connect your app to Firebase.

4. Update the Firebase configuration:
   - Locate the `google-services.json` file provided by Firebase.
   - Place the `google-services.json` file in the `app` directory of your project.

5. Open the `AndroidManifest.xml` file located in the `app/src/main` directory.

6. Add the following permissions to the `AndroidManifest.xml` file:

   <uses-permission android:name="android.permission.CAMERA" />
   <uses-feature android:name="android.hardware.camera.autofocus" />

7. Scan QR codes and barcodes by pointing the camera at them.

8. View the scanned data in the app and in the Firebase Realtime Database.

## Future Enhancements

- Add the ability to edit or delete scanned data.
- Implement user authentication to allow multiple users to manage their own data.
- Enhance UI/UX for a more visually appealing experience.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

- [Google Mobile Vision API Documentation](https://developers.google.com/android/reference/com/google/android/gms/vision/package-summary)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Kotlin Programming Language](https://kotlinlang.org/)
- [Android Studio Documentation](https://developer.android.com/studio)
