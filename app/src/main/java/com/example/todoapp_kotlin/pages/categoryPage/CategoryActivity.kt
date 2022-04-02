package com.example.todoapp_kotlin.pages.categoryPage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.TaskAdapter
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.pages.mainPage.MainActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.google.android.material.textfield.TextInputEditText

class CategoryActivity : AppCompatActivity(), TaskAdapter.TaskClickInterface,
    TaskAdapter.TaskDoneClickInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        supportActionBar?.hide()

        if(intent.getStringExtra("category")!=null ){

            findViewById<ImageView>(R.id.rollback).setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("category","that a category")
                startActivity(intent)
                finish()
            }

            val categoryName= intent.getStringExtra("category")
            val color= intent.getStringExtra("color")
            val id= intent.getStringExtra("id")!!.toInt()

            findViewById<TextView>(R.id.title).text = categoryName
            recyclerView = findViewById(R.id.recyclerview)
            // on below line we are setting layout
            // manager to our recycler view.
            recyclerView.layoutManager = LinearLayoutManager(this)
            // on below line we are initializing our adapter class.
            val taskAdapter = TaskAdapter(this, this,this)

            // on below line we are setting
            // adapter to our recycler view.
            recyclerView.adapter = taskAdapter
            recyclerView.setHasFixedSize(true)

            // on below line we are
            // initializing our view modal.
            viewModel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            )[MyViewModel::class.java]

            viewModel.caterogy.value = id.toString()

            // on below line we are calling all notes method
            // from our view modal class to observer the changes on list.
            viewModel.tasksByCategory.asLiveData().observe(this) { list ->
                list?.let {
                    // on below line we are updating our list.
                    taskAdapter.updateList(it)
                }
            }

            //add the onswipe to delete a note
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val task = taskAdapter.allTasks[viewHolder.adapterPosition]
                    viewModel.deleteTask(task)
                }
            }).attachToRecyclerView(recyclerView)

            findViewById<ImageView>(R.id.delete).setOnClickListener {
                viewModel.deleteTasksByCategoryName(viewModel.caterogy.value)
                viewModel.deleteCaterogy(Caterogy(id,categoryName!!,color!!))
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("category","this is a category")
                startActivity(intent)
                finish()
            }

            val new_category_dialog = Dialog(this)
            new_category_dialog.setContentView(R.layout.new_category)
            new_category_dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.back_round_white))

            val category = new_category_dialog.findViewById<AppCompatButton>(R.id.add)

            new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).setText(categoryName)
            category.text = "Edit"
            when(color){
                "blue" -> new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.blue)
                "pink" -> new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.pink)
                "green" -> new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.green)
                "purple" -> new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.purple)
                "beige" -> new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.beige)
            }

            findViewById<ImageView>(R.id.edit).setOnClickListener { new_category_dialog.show() }

            category.setOnClickListener {
                if(new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString().isNotEmpty()){
                    lateinit var color : String
                    when(new_category_dialog.findViewById<RadioGroup>(R.id.colors).checkedRadioButtonId){
                        R.id.blue -> color ="blue"
                        R.id.pink -> color ="pink"
                        R.id.green -> color ="green"
                        R.id.purple -> color ="purple"
                        R.id.beige -> color ="beige"
                    }
                    viewModel.updateCaterogy(Caterogy(id,new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString(),color))
                    findViewById<TextView>(R.id.title).text = new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString()
                    new_category_dialog.dismiss()
                }
            }
        }
    }

    override fun onEditClick(task: Task) {
        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra("id",task.idTask.toString())
        intent.putExtra("title",task.title)
        intent.putExtra("description",task.description)
        intent.putExtra("category",task.categoryName.toString())
        intent.putExtra("date",task.date)
        intent.putExtra("time",task.time)
        intent.putExtra("state",task.state.toString())
        startActivity(intent)    }

    override fun onDoneClick(task: Task) {
        if(task.state==0) viewModel.updateTask(Task(task.idTask,task.title,task.description,task.date,task.time,task.categoryName,1))
        else viewModel.updateTask(Task(task.idTask,task.title,task.description,task.date,task.time,task.categoryName,0))
    }
}