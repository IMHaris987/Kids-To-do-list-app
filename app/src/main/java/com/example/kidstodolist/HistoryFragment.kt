package com.example.kidstodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// Fragment that displays history-related cards or content in your app
// It inflates the layout file: fragment_history.xml
class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,       // Used to inflate layout resources into View objects
        container: ViewGroup?,          // The parent view that the fragment UI attaches to
        savedInstanceState: Bundle?     // Restores previous UI state if any
    ): View? {
        // Inflate and return the fragment's layout
        return inflater.inflate(R.layout.fragment_history, container, false)
    }
}
