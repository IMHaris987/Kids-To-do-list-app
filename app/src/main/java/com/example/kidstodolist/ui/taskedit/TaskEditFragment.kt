package com.example.kidstodolist.ui.taskedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kidstodolist.R

class TaskEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for editing a task
        return inflater.inflate(R.layout.fragment_task_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Animate bottom navigation bar appearing
        val bottomNav = view.findViewById<View>(R.id.bottom_nav)
        bottomNav.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(300)
            .start()

        // Back button returns to previous fragment
        view.findViewById<View>(R.id.back_button).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}