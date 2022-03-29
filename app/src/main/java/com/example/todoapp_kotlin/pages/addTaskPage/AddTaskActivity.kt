package com.example.todoapp_kotlin.pages.addTaskPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private lateinit var dateText : TextView
    private lateinit var timeText : TextView

    private var description = ""
    private var time = ""
    private var date = "anytime"
    private var category = "Uncategorized"

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedday = "0"
    private var savedmonth = "0"
    private var savedyear = "0"
    private var savedhour = "0"
    private var savedminute = "0"

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

        dateText = findViewById(R.id.datePicker)
        timeText = findViewById(R.id.timePicker)

        dateText.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this,this,year,month,day).show()
        }

        timeText.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(this,this,hour,minute,true).show()
        }

        findViewById<AppCompatButton>(R.id.add).setOnClickListener {
            val title = findViewById<TextInputEditText>(R.id.title_text).text.toString()
            Log.d("test", "title added = $title")
            findViewById<TextInputEditText>(R.id.Description).text?.let{ text->
                description=text.toString()
            }
            if(title.isNotEmpty()){
                Log.d("test","add the task")
                viewModel.insertTask(Task(title=title,description= description, time = time, date = date))
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Log.d("test","title empty")
                Toast.makeText(this,"please enter a title",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        savedday = dayofmonth.toString()
        savedmonth = month.toString()
        savedyear = year.toString()
        dateText.text = "$savedday / $savedmonth"
        date = "$savedday/$savedmonth/$savedyear"
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        savedhour = if(hour<10) "0$hour" else hour.toString()
        savedminute = if(minute<10) "0$minute" else minute.toString()

        timeText.text = "$savedhour:$savedminute"
        time  = "$savedhour:$savedminute"
    }
}

