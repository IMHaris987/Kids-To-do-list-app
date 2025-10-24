package com.example.kidstodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// ✅ Simple Fragment class that inflates the "fragment_action" layout
class ActionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for this fragment
        return inflater.inflate(R.layout.fragment_action, container, false)
    }
}
