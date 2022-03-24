package com.example.todoapp_kotlin.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task

class TaskAdapter (
    val context: Context,
    val taskClickInterface: TaskClickInterface,
    val taskDoneClickInterface: TaskDoneClickInterface
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    // on below line we are creating a
    // variable for our all notes list.
    private val allTasks = ArrayList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_item,
            parent, false
        )
        return TaskViewHolder(itemView)    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.title.setText(allTasks.get(position).title)
        holder.description.setText(allTasks.get(position).description)
        TODO("add the colors")
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allTasks.size
    }

    inner class TaskViewHolder(itView: View) :
        RecyclerView.ViewHolder(itView){
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val image = itemView.findViewById<View>(R.id.image)
        val title = itemView.findViewById<TextView>(R.id.text_view_title)
        val description = itemView.findViewById<TextView>(R.id.text_view_description)
        val color = itemView.findViewById<LinearLayout>(R.id.color)
        val task = itemView.findViewById<RelativeLayout>(R.id.EditLayout)
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