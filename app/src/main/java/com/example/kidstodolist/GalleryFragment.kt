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

class GalleryFragment : Fragment() {

    private lateinit var btnOpenCamera: Button
    private lateinit var btnOpenGallery: Button
    private lateinit var imgPreview: ImageView

    // Launcher for Gallery
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imgPreview.setImageURI(it) }
    }

    // Launcher for Camera
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { imgPreview.setImageBitmap(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        btnOpenCamera = view.findViewById(R.id.btnOpenCamera)
        btnOpenGallery = view.findViewById(R.id.btnOpenGallery)
        imgPreview = view.findViewById(R.id.imgPreview)

        btnOpenGallery.setOnClickListener {
            // Open gallery
            galleryLauncher.launch("image/*")
        }

        btnOpenCamera.setOnClickListener {
            // Open camera
            cameraLauncher.launch(null)
        }

        return view
    }
}
