package com.example.kidstodolist.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kidstodolist.R
import com.example.kidstodolist.ui.createcard.CreateCardActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        val fabCreate: FloatingActionButton = view.findViewById(R.id.fabCreate)

        fabCreate.setOnClickListener {
            // Launch CreateCardActivity when FAB is clicked
            val intent = Intent(requireContext(), CreateCardActivity::class.java)
            startActivity(intent)

            // Add smooth transition animation
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }

        return view
    }
}