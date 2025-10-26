package com.example.kidstodolist

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class CreateCardActivity : BaseActivity() {

    private lateinit var imagePreview: ImageView
    private var photoUri: Uri? = null

    companion object {
        private const val CAMERA_PERMISSION_CODE = 101
    }

    // Camera launcher expects a NON-NULL Uri when launched
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                // Use the stored photoUri (non-null when launched)
                photoUri?.let { uri ->
                    imagePreview.setImageURI(uri)

                    // Pass the image to VoiceInputFragment
                    val fragment = VoiceInputFragment().apply {
                        arguments = Bundle().apply {
                            putString("image_uri", uri.toString())
                        }
                    }

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right
                        )
                        .replace(android.R.id.content, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                Toast.makeText(this, "Camera canceled", Toast.LENGTH_SHORT).show()
            }
        }

    // Gallery: use GetContent which returns a Uri?
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imagePreview.setImageURI(uri)
            } else {
                Toast.makeText(this, "No photo selected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        imagePreview = findViewById(R.id.imagePreview)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val cardTake = findViewById<androidx.cardview.widget.CardView>(R.id.card_take)
        val cardChoose = findViewById<androidx.cardview.widget.CardView>(R.id.card_choose)

        ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        cardTake.setOnClickListener {
            if (hasCameraPermission()) openCamera()
            else requestCameraPermission()
        }

        cardChoose.setOnClickListener {
            pickImageFromGallery()
        }
    }

    /** ---------------- CAMERA ---------------- **/

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE
        )
    }

    private fun openCamera() {
        // Create the file first
        val imageFile = File.createTempFile("photo_", ".jpg", externalCacheDir)

        // Create a local non-null Uri and launch with it immediately
        val uriForLaunch: Uri = FileProvider.getUriForFile(
            this, "${packageName}.provider", imageFile
        )

        // store it for later use in result callback
        photoUri = uriForLaunch

        // Launch the camera with a non-null Uri
        cameraLauncher.launch(uriForLaunch)
    }

    /** ---------------- GALLERY ---------------- **/

    private fun pickImageFromGallery() {
        galleryLauncher.launch("image/*")
    }

    /** ---------------- PERMISSIONS ---------------- **/

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
            else
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}
