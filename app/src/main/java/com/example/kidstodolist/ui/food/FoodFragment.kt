package com.example.kidstodolist.ui.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kidstodolist.R

// Fragment to display food-related cards or tasks
// It inflates the layout resource: fragment_food.xml
class FoodFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,        // Used to inflate layout resources
        container: ViewGroup?,           // Parent view for the fragment
        savedInstanceState: Bundle?      // Previously saved state
    ): View? {
        // Inflate and return the fragment layout
        return inflater.inflate(R.layout.fragment_food, container, false)
    }
}