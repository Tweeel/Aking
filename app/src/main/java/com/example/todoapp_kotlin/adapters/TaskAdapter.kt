package com.example.todoapp_kotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Task

class TaskAdapter(
    val context: Context,
    private val taskClickInterface: TaskClickInterface?=null,
    private val parentClickInterface: ParentAdapter.TaskClickInterfaceParent?=null,
    private val collapsedClickInterface: CollapsedAdapter.TaskClickInterfaceCollapsed?=null,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    // on below line we are creating a
    // variable for our all notes list.
    val allTasks = ArrayList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_item,
            parent, false
        )
        return TaskViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.title.text = allTasks[position].title
        holder.description.text = allTasks[position].description

        holder.task.setOnClickListener{
            collapsedClickInterface?.onEditClick(allTasks[position])
            parentClickInterface?.onEditClick(allTasks[position])
            taskClickInterface?.onEditClick(allTasks[position])
        }
        holder.image.setOnClickListener{
            collapsedClickInterface?.onDoneClick(allTasks[position])
            parentClickInterface?.onDoneClick(allTasks[position])
            taskClickInterface?.onDoneClick(allTasks[position])
        }


        when(allTasks[position].categoryColor){
            "black" -> holder.color.setBackgroundResource(R.color.black)
            "pink" -> holder.color.setBackgroundResource(R.color.button_pink)
            "blue" -> holder.color.setBackgroundResource(R.color.button_blue)
            "green" -> holder.color.setBackgroundResource(R.color.button_green)
            "purple" -> holder.color.setBackgroundResource(R.color.button_purple)
            "beige" -> holder.color.setBackgroundResource(R.color.button_beige)
        }

        if(allTasks[position].state==0){
            holder.title.paintFlags = 0
            holder.description.paintFlags = 0
            when (allTasks[position].categoryColor){
                "black" -> holder.image.setBackgroundResource(R.drawable.uncheck_black)
                "pink" -> holder.image.setBackgroundResource(R.drawable.uncheck_pink)
                "blue" -> holder.image.setBackgroundResource(R.drawable.uncheck_blue)
                "green" -> holder.image.setBackgroundResource(R.drawable.uncheck_green)
                "purple" -> holder.image.setBackgroundResource(R.drawable.uncheck_purple)
                "beige" -> holder.image.setBackgroundResource(R.drawable.uncheck_beige)
            }
        }else{
            holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.description.paintFlags = holder.description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            when (allTasks[position].categoryColor){
                "black" -> holder.image.setBackgroundResource(R.drawable.check_black)
                "pink" -> holder.image.setBackgroundResource(R.drawable.check_pink)
                "blue" -> holder.image.setBackgroundResource(R.drawable.check_blue)
                "green" -> holder.image.setBackgroundResource(R.drawable.check_green)
                "purple" -> holder.image.setBackgroundResource(R.drawable.check_purple)
                "beige" -> holder.image.setBackgroundResource(R.drawable.check_beige)
            }
        }
    }

    override fun getItemCount() = allTasks.size

    // below method is use to update our list of notes.
    fun updateList(newList: List<Task>) {
        // on below line we are clearing
        // our notes array list
        allTasks.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allTasks.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
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
        fun onEditClick(task: Task)
        fun onDoneClick(task: Task)
    }
}