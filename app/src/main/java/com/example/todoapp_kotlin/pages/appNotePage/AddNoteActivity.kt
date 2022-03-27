package com.example.todoapp_kotlin.pages.appNotePage

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel

class AddNoteActivity : AppCompatActivity() {

    lateinit var color : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

        findViewById<ImageView>(R.id.rollback).setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.done).setOnClickListener{
            val note = findViewById<EditText>(R.id.note).text.toString()
            when(findViewById<RadioGroup>(R.id.colors).checkedRadioButtonId){
                R.id.blue -> color ="blue"
                R.id.pink -> color ="pink"
                R.id.green -> color ="green"
                R.id.purple -> color ="purple"
                R.id.beige -> color ="beige"
            }
            if(note.isNotEmpty()){
                viewModel.insertNote(Note(null,note,color))
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"please fill the note",Toast.LENGTH_LONG).show()
            }

        }
    }
}