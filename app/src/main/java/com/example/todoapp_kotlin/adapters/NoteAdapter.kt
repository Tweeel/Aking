package com.example.todoapp_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Note

class NoteAdapter (
    val context: Context,
    val noteClickInterface: NoteClickInterface
        ) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    // on below line we are creating a
    // variable for our all notes list.
    private val allNotes = ArrayList<Note>()

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
        holder.text.setText(allNotes.get(position).text)
        TODO("add the category")

        // on below line we are adding click listener
        // to our recycler view item.
        holder.itemView.setOnClickListener {
            // on below line we are calling a note click interface
            // and we are passing a position to it.
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allNotes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val category = itemView.findViewById<View>(R.id.category)
        val text = itemView.findViewById<TextView>(R.id.text)
    }

    interface NoteClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onNoteClick(note: Note)
    }
}