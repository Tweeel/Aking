package com.example.todoapp_kotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
import com.google.android.material.snackbar.Snackbar

class CollapsedAdapter(
    val context: Context,
    private val taskClickInterface: TaskClickInterfaceCollapsed,
    private val collapsedInterface: CollapsedInterface,
    private val viewmodel: MyViewModel
): RecyclerView.Adapter<CollapsedAdapter.CollabsedViewHolder>() {

    // on below line we are creating a
    // variable for our all notes list.
    private val allCollapsed = ArrayList<Collapsed>()


    override fun onCreateViewHolder(collapsed: ViewGroup, viewType: Int): CollabsedViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(collapsed.context).inflate(
            R.layout.collapsed_item,
            collapsed, false
        )
        return CollabsedViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CollabsedViewHolder, position: Int) {
        holder.title.text = allCollapsed[position].title

        holder.recyclerview.layoutManager = LinearLayoutManager(context)

        if(allCollapsed[position].collapsed == true){
            holder.taskAdapter.updateList(allCollapsed[position].tasks!!)
            holder.arrow.setBackgroundResource(R.drawable.arrow_up)
        }else{
            holder.taskAdapter.updateList(emptyList())
            holder.arrow.setBackgroundResource(R.drawable.arrow_down)
        }
        holder.recyclerview.adapter = holder.taskAdapter

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
            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(holder.taskAdapter.allTasks.size!=0) {
                    val task = holder.taskAdapter.allTasks[viewHolder.adapterPosition]
                    viewmodel.deleteTask(task)
                    holder.taskAdapter.allTasks.removeAt(viewHolder.adapterPosition)
                    holder.taskAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                    Snackbar.make(holder.recyclerview, "are you sure you wanna delete the task", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            viewmodel.insertTask(task)
                            holder.taskAdapter.notifyDataSetChanged()
                        }.setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.BLACK)
                        .setActionTextColor(context.getColor(R.color.pink))
                        .show()
                }else{
                    Toast.makeText(context, "no tasks to delete", Toast.LENGTH_SHORT).show()
                }
            }
        }).attachToRecyclerView(holder.recyclerview)
    }

    override fun getItemCount() = allCollapsed.size

    // below method is use to update our list of notes.
    @SuppressLint("NotifyDataSetChanged")
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
        val title: TextView = itemView.findViewById(R.id.title)
        val recyclerview: RecyclerView = itemView.findViewById(R.id.tasks)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.collapsed)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
        val taskAdapter = TaskAdapter( recyclerview.context, collapsedClickInterface = taskClickInterface)

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