package com.example.todoapp_kotlin.pages.addTaskPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.note_item.view.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

        findViewById<ImageView>(R.id.rollback).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<AppCompatButton>(R.id.add).setOnClickListener {
            val title = findViewById<TextInputEditText>(R.id.title_text).text.toString()
            Log.d("test", "title added = $title")
            var description = ""
            findViewById<TextInputLayout>(R.id.editTexDescription).text?.let{ it->
                description=findViewById<TextInputEditText>(R.id.editTexDescription).text.toString()
                Log.d("test","description added = $description")
            }
            Log.d("test","description = $description")

            if(title.isNotEmpty()){
                Log.d("test","add the task")
                viewModel.insertTask(Task(title=title,description= description))
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Log.d("test","title empty")
                Toast.makeText(this,"please enter a title",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

