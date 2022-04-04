package com.example.todoapp_kotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Caterogy

class CategoryAdapter (
    val context: Context,
    private val categoryClickInterface: CategoryAdapter.CategoryClickInterface
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    // on below line we are creating a
    // variable for our all categories list.
    private val allCategories = ArrayList<Caterogy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item,
            parent, false
        )
        return CategoryViewHolder(itemView)    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.text.text = allCategories[position].categoryName
//        holder.number.text = allCategories[position].tasksNumber.toString()+" Tasks"
        when(allCategories[position].color){
            "blue" -> holder.color.setBackgroundResource(R.drawable.check_blue)
            "pink" -> holder.color.setBackgroundResource(R.drawable.check_pink)
            "green" -> holder.color.setBackgroundResource(R.drawable.check_green)
            "purple" -> holder.color.setBackgroundResource(R.drawable.check_purple)
            "beige" -> holder.color.setBackgroundResource(R.drawable.check_beige)
        }

        // on below line we are adding click listener
        // to our recycler view item.
        holder.itemView.setOnClickListener {
            // on below line we are calling a note click interface
            // and we are passing a position to it.
            categoryClickInterface.oneCategoryClick(allCategories[position])
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allCategories.size
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Caterogy>) {
        // on below line we are clearing
        // our notes array list
        allCategories.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allCategories.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val color = itemView.findViewById<View>(R.id.color)
        val text = itemView.findViewById<TextView>(R.id.title)
//        val number = itemView.findViewById<TextView>(R.id.number)
    }

    interface CategoryClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun oneCategoryClick(caterogy: Caterogy)
    }
}