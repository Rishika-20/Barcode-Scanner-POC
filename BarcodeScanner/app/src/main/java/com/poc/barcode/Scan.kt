package com.poc.barcode

/**
 * Barcode Scanning and Firebase Integration - Scan Activity
 *
 * This activity captures barcodes using the device's camera and stores them in a Firebase Realtime Database.
 *
 * Features:
 * - Barcode scanning using the Google Mobile Vision API.
 * - Storing barcode data in a Firebase Realtime Database.
 * - Checking for existing barcode data in the database.
 * - Displaying messages using Toast messages.
 */

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.poc.barcode.databinding.ActivityScanBinding
import java.io.IOException

class Scan : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var databaseReference: DatabaseReference

    private var intentData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeFirebase()
        initializeBarcodeScanner()
    }

    /**
     * Initializes the Firebase components.
     */
    private fun initializeFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ListOfItemsScanned")
    }

    /**
     * Initializes the BarcodeDetector and CameraSource for barcode detection.
     */
    private fun initializeBarcodeScanner() {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    cameraSource.start(binding.surfaceView!!.holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                // Not implemented
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Your scanner has been stopped!!", Toast.LENGTH_SHORT).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    handleDetectedBarcode(barcodes.valueAt(0).displayValue)
                }
            }
        })
    }

    /**
     * Handles detected barcodes.
     * Updates UI elements to display the detected barcode value and offers a button to store it in the database.
     */
    private fun handleDetectedBarcode(barcodeValue: String) {
        binding.txtView!!.post {
            binding.btnAction!!.text = "Scan the Items"
            intentData = barcodeValue
            binding.txtView.setText(intentData)

            binding.btnAction.setOnClickListener {
                storeBarcodeInFirebase(intentData)
            }
        }
    }

    /**
     * Stores barcode data in the Firebase Realtime Database after checking for duplicates.
     */
    private fun storeBarcodeInFirebase(barcodeValue: String) {
        // Check if the barcode data already exists in the database
        val query = databaseReference.orderByChild("barcodeValue").equalTo(barcodeValue)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showSnackbar("Items already scanned!!")
                } else {
                    // Add the detected barcode data to Firebase Database
                    val barcodeId = databaseReference.push().key
                    val barcode = mapOf("barcodeValue" to barcodeValue)
                    if (barcodeId != null) {
                        databaseReference.child(barcodeId).setValue(barcode)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showSnackbar(" Item successfully added to the database!!")
                                } else {
                                    showSnackbar("Failed to add Item to the database")
                                }
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showSnackbar("Database error: ${error.message}")
            }
        })
    }


    /**
     * Displays a Toast message.
     */
    private fun showSnackbar(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        cameraSource.release()
    }

    override fun onResume() {
        super.onResume()
        initializeBarcodeScanner()
    }
}
