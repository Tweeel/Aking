package com.example.todoapp_kotlin.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note

class NoteAdapter (
    val context: Context,

    private val noteClickInterface: NoteAdapter.NoteClickInterface
        ) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    // on below line we are creating a
    // variable for our all notes list.
    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent, false
        )
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.text.text = allNotes[position].text

        when(allNotes[position].color){
            "blue" -> holder.category.setBackgroundColor(Color.parseColor("#6174F9"))
            "pink" -> holder.category.setBackgroundColor(Color.parseColor("#E52B6A"))
            "green" -> holder.category.setBackgroundColor(Color.parseColor("#5BBB56"))
            "purple" -> holder.category.setBackgroundColor(Color.parseColor("#3D3B62"))
            "beige" -> holder.category.setBackgroundColor(Color.parseColor("#F5CA90"))
            else -> holder.category.setBackgroundColor(Color.parseColor("#000000"))
        }

        // on below line we are adding click listener
        // to our recycler view item.
        holder.itemView.setOnClickListener {
            // on below line we are calling a note click interface
            // and we are passing a position to it.
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allNotes.size
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Note>) {
        // on below line we are clearing
        // our notes array list
        allNotes.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allNotes.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val category: View = itemView.findViewById(R.id.category)
        val text: TextView = itemView.findViewById(R.id.text)
    }

    interface NoteClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onNoteClick(note: Note)
    }
}
