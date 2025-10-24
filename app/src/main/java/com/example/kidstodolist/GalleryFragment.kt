package com.example.kidstodolist

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

// Fragment that lets the user either open the camera or select an image from the gallery
// Displays the selected or captured image in an ImageView
class GalleryFragment : Fragment() {

    // UI components
    private lateinit var btnOpenCamera: Button
    private lateinit var btnOpenGallery: Button
    private lateinit var imgPreview: ImageView

    // Gallery launcher — opens device gallery and returns selected image URI
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imgPreview.setImageURI(it) } // Display selected image
    }

    // Camera launcher — opens device camera and returns captured image as a Bitmap
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { imgPreview.setImageBitmap(it) } // Display captured image
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for gallery fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        // Initialize UI elements
        btnOpenCamera = view.findViewById(R.id.btnOpenCamera)
        btnOpenGallery = view.findViewById(R.id.btnOpenGallery)
        imgPreview = view.findViewById(R.id.imgPreview)

        // Button click to open gallery
        btnOpenGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Button click to open camera
        btnOpenCamera.setOnClickListener {
            cameraLauncher.launch(null)
        }

        return view
    }
}
