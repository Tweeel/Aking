package com.example.todoapp_kotlin.pages.appNotePage

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel

class AddNoteActivity : AppCompatActivity() {

    lateinit var color : String
    var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

        /*receive data from the coming note*/
        val intent = intent
        if(intent.getStringExtra("id")!=null &&
            intent.getStringExtra("text")!=null &&
            intent.getStringExtra("color")!=null){
                findViewById<TextView>(R.id.title).text = "Edit Note"
                id = intent.getStringExtra("id")!!.toInt()
                val text = intent.getStringExtra("text")
                val color = intent.getStringExtra("color")

                val radioButton =  findViewById<RadioGroup>(R.id.colors)

                findViewById<EditText>(R.id.note).setText(text)
                when(color){
                    "blue" -> radioButton.findViewById<RadioButton>(R.id.blue).isChecked = true
                    "pink" -> radioButton.findViewById<RadioButton>(R.id.pink).isChecked = true
                    "green" -> radioButton.findViewById<RadioButton>(R.id.green).isChecked = true
                    "purple" -> radioButton.findViewById<RadioButton>(R.id.purple).isChecked = true
                    "beige" -> radioButton.findViewById<RadioButton>(R.id.beige).isChecked = true
                }
        }

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
                if(id==0)
                    viewModel.insertNote(Note(text=note,color=color))
                else
                    viewModel.updateNote(Note(id,note,color))
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"please fill the note",Toast.LENGTH_LONG).show()
            }

        }
    }
}