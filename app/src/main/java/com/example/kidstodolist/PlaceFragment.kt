package com.example.kidstodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Fragment that represents a "Place" section in your app
// It inflates a layout resource (fragment_place.xml)
// This fragment can display place-related tasks or categories depending on app design
class PlaceFragment : Fragment() {

    // Called to create and return the view hierarchy associated with the fragment
    override fun onCreateView(
        inflater: LayoutInflater,          // Used to inflate the XML layout
        container: ViewGroup?,             // Parent view that this fragment will be attached to
        savedInstanceState: Bundle?        // Contains previously saved state (if any)
    ): View? {
        // Inflate the layout resource for this fragment
        return inflater.inflate(R.layout.fragment_place, container, false)
    }
}
