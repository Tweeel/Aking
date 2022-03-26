package com.example.todoapp_kotlin.pages.appNotePage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp_kotlin.R

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.hide()

    }
}