package com.example.kidstodolist

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddTaskActivity : BaseActivity() {

    private lateinit var backIcon: ImageView
    private lateinit var tickIcon: ImageView
    private lateinit var taskNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        backIcon = findViewById(R.id.backIcon)
        tickIcon = findViewById(R.id.tickIcon)
        taskNameEditText = findViewById(R.id.taskNameEditText)

        // ✅ Back icon → finish activity
        backIcon.setOnClickListener{
            finish()
        }

        // ✅ Tick icon → save task (for now just return)
        tickIcon.setOnClickListener {
            val taskName = taskNameEditText.text.toString().trim()
            if (taskName.isNotEmpty()) {
                // TODO: Pass result back to MainActivity if needed
                finish()
            } else {
                taskNameEditText.error = "Please enter a task name"
            }
        }
        
        val plusIcon: ImageView = findViewById(R.id.plusIcon)
        plusIcon.setOnClickListener {
            val intent = Intent(this, ChooseCardActivity::class.java)
            startActivity(intent)
        }
    }
}
