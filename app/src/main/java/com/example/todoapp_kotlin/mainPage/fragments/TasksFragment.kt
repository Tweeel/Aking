package com.example.todoapp_kotlin.mainPage.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.TaskDatabase
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Task
import kotlinx.coroutines.launch

class TasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*database creation*/
        val dao = activity?.let { TaskDatabase.getInstance(it).taskDao }

        val categories = listOf(
            Caterogy("sport"),
            Caterogy("study"),
            Caterogy("work"),
        )

        val tasks = listOf(
            Task(0,"task 1", "21/03/2022","21:30","sport",0),
            Task(0,"task 2", "21/03/2022","22:30","study",1),
            Task(0,"task 3", "20/03/2022","08:00","work",0),
            Task(0,"task 4", "20/03/2022","10:30","sport",1),
            Task(0,"task 5", "22/03/2022","15:15","work",0),
        )

        lifecycleScope.launch {
            categories.forEach { dao?.insertCategory(it) }
            tasks.forEach { dao?.insertTask(it) }
            //how to get data
            val categorywithtask = dao?.getTaskAWithCategorie("sport")
            categorywithtask?.first()?.task
        }
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