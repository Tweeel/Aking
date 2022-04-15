package com.example.todoapp_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Collapsed
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.mainPage.fragments.MonthFragment
import com.example.todoapp_kotlin.viewmodels.MyViewModel

class CollapsedAdapter(
    val context: Context,
    private val taskClickInterface: TaskClickInterfaceCollapsed,
    private val collapsedInterface: CollapsedInterface,
    private val viewmodel: MyViewModel
): RecyclerView.Adapter<CollapsedAdapter.CollabsedViewHolder>() {

    // on below line we are creating a
    // variable for our all notes list.
    val allCollapsed = ArrayList<Collapsed>()
    lateinit var taskAdapter : TaskAdapter


    override fun onCreateViewHolder(collapsed: ViewGroup, viewType: Int): CollabsedViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(collapsed.context).inflate(
            R.layout.collapsed_item,
            collapsed, false
        )
        return CollabsedViewHolder(itemView)    }

    override fun onBindViewHolder(holder: CollabsedViewHolder, position: Int) {
        holder.title.text = allCollapsed[position].title

        holder.recyclerview.layoutManager = LinearLayoutManager(context)
        taskAdapter = TaskAdapter( holder.recyclerview.context, collapsedClickInterface = taskClickInterface)
        if(allCollapsed[position].collapsed == true){
            taskAdapter.updateList(allCollapsed[position].tasks)
            holder.recyclerview.adapter = taskAdapter
            holder.arrow.setBackgroundResource(R.drawable.arrow_up)
        }else{
            taskAdapter.updateList(emptyList())
            holder.recyclerview.adapter = taskAdapter
            holder.arrow.setBackgroundResource(R.drawable.arrow_down)
        }

        holder.constraintLayout.setOnClickListener {
            collapsedInterface.onCollapsedClick(allCollapsed[position])
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = taskAdapter.allTasks[viewHolder.adapterPosition]
                viewmodel.deleteTask(task)
                Toast.makeText(context,"task deleted", Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(holder.recyclerview)
    }

    override fun getItemCount() = allCollapsed.size

    // below method is use to update our list of notes.
    fun updateList(newList: List<Collapsed>) {
        // on below line we are clearing
        // our notes array list
        allCollapsed.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allCollapsed.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

    inner class CollabsedViewHolder(itView: View) :
        RecyclerView.ViewHolder(itView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val recyclerview = itemView.findViewById<RecyclerView>(R.id.tasks)
        val constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.collapsed)
        val arrow = itemView.findViewById<ImageView>(R.id.arrow)
    }

    interface TaskClickInterfaceCollapsed {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onEditClick(task: Task)
        fun onDoneClick(task: Task)
    }

    interface  CollapsedInterface{
        fun onCollapsedClick(collapsed: Collapsed)
    }

    fun MonthFragment.delete(){
        Toast.makeText(context,"task deleted", Toast.LENGTH_LONG).show()
    }
}