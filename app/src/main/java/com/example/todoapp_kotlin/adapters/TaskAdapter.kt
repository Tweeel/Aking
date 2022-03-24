package com.example.todoapp_kotlin.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.database.entities.Note

class TaskAdapter (
    val context: Context,
    val taskClickInterface: TaskClickInterface,
    val taskDoneClickInterface: TaskDoneClickInterface
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class TaskViewHolder(itView: View) :
        RecyclerView.ViewHolder(itView){
    }

    interface TaskClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onEditClick(note: Note)
    }

    interface TaskDoneClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onDoneClick(note: Note)
    }
}