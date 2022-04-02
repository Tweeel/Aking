package com.example.todoapp_kotlin.pages.addTaskPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private lateinit var titleText : TextView
    private lateinit var descriptionText : TextView
    private lateinit var caegoryText : TextView
    private lateinit var dateText : TextView
    private lateinit var timeText : TextView

    private var description = ""
    private var time = "Anyday"
    private var date = "Anytime"
    private var category = "Uncategorized"
    private var state = 0

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

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

        val menu = findViewById<TextView>(R.id.category)
        registerForContextMenu(menu)
        menu.setOnClickListener{
            Log.d("test","test")
            val popup = PopupMenu(this, menu)
            popup.menuInflater.inflate(R.menu.toolbar_menu, popup.menu)
            popup.show()
        }

        findViewById<ImageView>(R.id.rollback).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        titleText = findViewById(R.id.title_text)
        descriptionText = findViewById(R.id.Description)
        dateText = findViewById(R.id.datePicker)
        timeText = findViewById(R.id.timePicker)
        caegoryText = findViewById(R.id.category)

        /*receive data from the coming note*/
        if(intent.getStringExtra("id")!=null &&
            intent.getStringExtra("title")!=null){
            id = intent.getStringExtra("id")!!.toInt()
            Log.d("test",id.toString())
            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("description")
            val category = intent.getStringExtra("category")
            val date = intent.getStringExtra("date")
            val time = intent.getStringExtra("time")
            val state = intent.getStringExtra("state")!!.toInt()

            findViewById<TextView>(R.id.title).text = "Edit Task"
            findViewById<TextView>(R.id.add).text = "Edit"
            titleText.text = title
            descriptionText.text = description
            category?.let{
                caegoryText.text = category
                this.category = category
            }

            date?.let{
                dateText.text = date.dropLast(5)
                this.date=date
            }

            time?.let{
                timeText.text = time
                this.time=time
            }
            this.state=state
        }

        dateText.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this,this,year,month,day).show()
        }

        timeText.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(this,this,hour,minute,true).show()
        }

        findViewById<AppCompatButton>(R.id.add).setOnClickListener {
            val title = titleText.text.toString()
            descriptionText.text?.let{ text->
                description=text.toString()
            }
            if(title.isNotEmpty()){
                if(id==0)
                    viewModel.insertTask(Task(title=title,description= description,
                        categoryName = category,time = time, date = date))
                else
                    viewModel.updateTask(Task(idTask =id,title=title,description= description,
                        categoryName = category, time = time, date = date, state = state))

                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
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
        savedday = if(dayofmonth<10) "0$dayofmonth" else dayofmonth.toString()
        savedmonth = if(month<10) "0$month" else month.toString()
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

