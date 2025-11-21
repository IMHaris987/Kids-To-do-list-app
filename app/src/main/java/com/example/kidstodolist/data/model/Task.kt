package com.example.kidstodolist.data.model

// Data class representing a single task item in the to-do list
// Each task has a title (String) and an icon resource ID (Int)
data class Task(
    val title: String,      // The name or label of the task
    val iconResId: Int      // Drawable resource ID for the task's icon (e.g., R.drawable.ic_task)
)