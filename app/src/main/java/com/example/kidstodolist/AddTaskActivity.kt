package com.example.kidstodolist

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

// Activity that allows users to add a new task to their to-do list
// Provides input for task name and options to navigate back or confirm the task addition
class AddTaskActivity : BaseActivity() {

    // UI elements
    private lateinit var backIcon: ImageView        // Navigates back to the previous screen020*-----------
    private lateinit var tickIcon: ImageView        // Confirms and saves the task
    private lateinit var taskNameEditText: EditText // Input field for the task title

    // Lifecycle method called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)  // Inflate layout for this screen

        // Initialize UI elements from layout
        backIcon = findViewById(R.id.backIcon)
        tickIcon = findViewById(R.id.tickIcon)
        taskNameEditText = findViewById(R.id.taskNameEditText)

        // 🔙 Back icon: close the current activity and go back
        backIcon.setOnClickListener {
            finish()
        }

        // ✅ Tick icon: validate input and save task
        tickIcon.setOnClickListener {
            val taskName = taskNameEditText.text.toString().trim() // Get text input
            if (taskName.isNotEmpty()) {
                // TODO: Send result back to MainActivity (if implementing data transfer)
                finish() // Close activity after saving task
            } else {
                // Show error if user tries to save an empty task
                taskNameEditText.error = "Please enter a task name"
            }
        }

        // ➕ Plus icon: navigate to ChooseCardActivity to select a task card
        val plusIcon: ImageView = findViewById(R.id.plusIcon)
        plusIcon.setOnClickListener {
            val intent = Intent(this, ChooseCardActivity::class.java)
            startActivity(intent)
        }
    }
}
