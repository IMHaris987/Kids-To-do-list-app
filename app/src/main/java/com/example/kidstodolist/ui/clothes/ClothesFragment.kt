package com.example.kidstodolist.ui.clothes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kidstodolist.R

// Fragment that displays clothing-related items or cards
// It inflates fragment_clothes.xml for UI
class ClothesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate and return layout
        return inflater.inflate(R.layout.fragment_clothes, container, false)
    }
}