package com.example.todoapp_kotlin.pages.appNotePage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddNoteActivity : AppCompatActivity() {

    lateinit var color : String
    var id : Int = 0
    var date = "created"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.hide()

        val intentToMain = Intent(this,MainActivity::class.java)

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

        val currentDate= LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = currentDate.format(formatter)

        findViewById<TextView>(R.id.date).text = "Created $date"

        /*receive data from the coming note*/
        if(intent.getStringExtra("id")!=null &&
            intent.getStringExtra("text")!=null &&
            intent.getStringExtra("color")!=null){
                findViewById<TextView>(R.id.title).text = "Edit Note"
                id = intent.getStringExtra("id")!!.toInt()
                val text = intent.getStringExtra("text")
                val color = intent.getStringExtra("color")
                val thisdate = intent.getStringExtra("date")!!
                val version = intent.getStringExtra("version")!!.toInt()
                val radioButton =  findViewById<RadioGroup>(R.id.colors)
                if(version==1)  findViewById<TextView>(R.id.date).text = "Created $thisdate"
                else findViewById<TextView>(R.id.date).text = "Edited $thisdate"


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
            intentToMain.putExtra("note","that a note")
            startActivity(intentToMain)
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
                    viewModel.insertNote(Note(text=note,color=color,date=date, version = 1))
                else
                    viewModel.updateNote(Note(idTile = id,text=note,color=color,date=date, version = 2))
                intentToMain.putExtra("note","that a note")
                startActivity(intentToMain)
                finish()
            }else{
                Toast.makeText(this,"please fill the note",Toast.LENGTH_LONG).show()
            }
        }
    }
}