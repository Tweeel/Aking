package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.TaskAdapter
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.viewmodels.MyViewModel

class TasksFragment : Fragment(), TaskAdapter.TaskClickInterface,
    TaskAdapter.TaskDoneClickInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var menu: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on below line we are initializing
        // all our variables.
        menu = view.findViewById(R.id.menu)
        recyclerView = view.findViewById(R.id.recyclerview)
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        val noteRVAdapter = TaskAdapter(requireActivity(), this,this)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = noteRVAdapter
        recyclerView.setHasFixedSize(true)

        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModel.allTasks.observe(requireActivity()) { list ->
            list?.let {
                // on below line we are updating our list.
                noteRVAdapter.updateList(it)
            }
        }

        registerForContextMenu(menu)
        menu.setOnClickListener{
            Log.d("test","test")
            val popup = PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.toolbar_menu, popup.menu)
            popup.show()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.toolbar_menu,menu)
    }

    override fun onEditClick(task: Task) {
    }

    override fun onDoneClick(task: Task) {
    }

}