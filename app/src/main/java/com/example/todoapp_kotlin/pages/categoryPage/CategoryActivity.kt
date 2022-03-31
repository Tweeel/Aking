package com.example.todoapp_kotlin.pages.categoryPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.TaskAdapter
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import kotlinx.android.synthetic.main.note_item.*

class CategoryActivity : AppCompatActivity(), TaskAdapter.TaskClickInterface,
    TaskAdapter.TaskDoneClickInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        supportActionBar?.hide()


        if(intent.getStringExtra("category")!=null ){
            val categoryName= intent.getStringExtra("category")
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
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
            )[MyViewModel::class.java]
            viewModel.caterogy = categoryName!!
            // on below line we are calling all notes method
            // from our view modal class to observer the changes on list.
            viewModel.tasksByCategory.observe(this) { list ->
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
        }
    }

    override fun onEditClick(task: Task) {
        TODO("Not yet implemented")
    }

    override fun onDoneClick(task: Task) {
        TODO("Not yet implemented")
    }
}