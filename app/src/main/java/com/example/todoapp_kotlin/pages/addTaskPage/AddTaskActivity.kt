package com.example.todoapp_kotlin.pages.addTaskPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp_kotlin.R

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.hide()

    }
}