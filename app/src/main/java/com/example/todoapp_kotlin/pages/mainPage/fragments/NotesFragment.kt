package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.NoteAdapter
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.pages.appNotePage.AddNoteActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment(), NoteAdapter.NoteClickInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerViewSetup()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this.activity, AddNoteActivity::class.java)
        intent.putExtra("id",note.idTile.toString())
        intent.putExtra("text",note.text)
        intent.putExtra("color",note.color)
        intent.putExtra("date",note.date)
        intent.putExtra("version",note.version.toString())
        startActivity(intent)
    }

    fun recyclerViewSetup(){
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        val noteAdapter = NoteAdapter(requireActivity(),this)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = noteAdapter
        recyclerView.setHasFixedSize(true)

        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModel.allNotes.observe(requireActivity()) { list ->
            list?.let {
                // on below line we are updating our list.
                noteAdapter.updateList(it)
            }
        }

        //add the onswipe to delete a note
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.allNotes[viewHolder.adapterPosition]
                viewModel.deleteNote(note)
                Snackbar.make(recyclerView, "are you sure you wanna delete the note", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.insertNote(note)
                        noteAdapter.notifyDataSetChanged()
                    }.setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK)
                    .setActionTextColor(context!!.getColor(R.color.pink))
                    .show()
                Toast.makeText(context,"task deleted", Toast.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(recyclerView)
    }
}