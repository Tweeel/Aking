package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.todoapp_kotlin.R

class TasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_tasks, container, false)
        val menu = view.findViewById<ImageView>(R.id.menu)
        registerForContextMenu(menu)
        menu.setOnClickListener{
            Log.d("test","test")
            val popup = PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.toolbar_menu, popup.menu)
            popup.show()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.toolbar_menu,menu)
    }
}