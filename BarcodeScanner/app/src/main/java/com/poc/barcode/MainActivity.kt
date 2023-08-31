package com.poc.barcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.poc.barcode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    /**
     * This is an instance of the ActivityResultLauncher<String> interface,
     * which is used to handle the result of requesting camera permission.
     * **/
    private var requestCamera : ActivityResultLauncher<String>? = null

    /**
     * This property is of type ActivityMainBinding, used to access UI elements
     * defined in the associated XML layout file (activity_main.xml).
     **/
    private lateinit var binding: ActivityMainBinding

    /**
     * The onCreate() method is called when the activity is being created.
     * It sets up the layout using the ActivityMainBinding class.
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         *     This method registers an ActivityResultLauncher for requesting camera permission.
         *     It uses the ActivityResultContracts.RequestPermission() contract,
         *         - which handles the permission request and its result.
         *     The second parameter is a lambda function that is executed when
         *         -the permission request result is received.
         **/

        requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission(),)
        {
            if(it){
                val intent = Intent(this,
                Scan:: class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * This method sets a click listener on the "Scan" button (btnScanner).
         *  When the button is clicked, the requestCamera?.launch() method
         *        -  is called to request camera permission.
         *  This launches the camera permission request using the ActivityResultLauncher defined earlier.
         *  It takes the permission string (android.Manifest.permission.CAMERA) as a parameter.
         *
         **/
        binding.btnScanner.setOnClickListener {
            requestCamera?.launch(android.Manifest.permission.CAMERA)
        }
    }
}