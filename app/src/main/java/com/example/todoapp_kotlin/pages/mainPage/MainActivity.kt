package com.example.todoapp_kotlin.pages.mainPage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.pages.appNotePage.AddNoteActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        /*setup the bottomNavigationView*/
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background =null
        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.tasksFragment,
            R.id.monthFragment,
            R.id.categiriesFragment,
            R.id.notesFragment
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        /*setup the fab*/
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val dialog_new = Dialog(this)
        dialog_new.setContentView(R.layout.create_new)
        dialog_new.window?.setBackgroundDrawable(getDrawable(R.drawable.back_round_white))
        dialog_new.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val window_new = dialog_new.window
        window_new?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        window_new?.setGravity(Gravity.CENTER)

        val task = dialog_new.findViewById<TextView>(R.id.task)
        val note = dialog_new.findViewById<TextView>(R.id.note)
        val list = dialog_new.findViewById<TextView>(R.id.list)

        task.setOnClickListener {
            startActivity(Intent(this,AddTaskActivity::class.java))
            dialog_new.dismiss()
        }
        note.setOnClickListener{
            startActivity(Intent(this,AddNoteActivity::class.java))
            dialog_new.dismiss()
        }
        list.setOnClickListener {  }

        fab.setOnClickListener {
            // Showing the dialog_new here
            dialog_new.show();
        }

//        fun dao() = TaskDatabase.getInstance(this).dao()
//        val notes = listOf(
//            Note(1,"note 1","sport"),
//            Note(2,"note 2","study"),
//            Note(3,"note 3","sport"),
//            Note(4,"note 4","work"),
//            Note(5,"note 5","work"),
//        )
//        val categories = listOf(
//            Caterogy("sport"),
//            Caterogy("study"),
//            Caterogy("work"),
//        )
//
//        val tasks = listOf(
//            Task(1,"task 1", "task 5 description","22/03/2022","15:15","work",0),
//            Task(2,"task 2", "task 5 description","22/03/2022","15:15","study",0),
//            Task(3,"task 3", "task 5 description","22/03/2022","15:15","sport",0),
//            Task(4,"task 4", "task 5 description","22/03/2022","15:15","study",0),
//            Task(5,"task 5", "task 5 description","22/03/2022","15:15","work",0)
//        )
//
//        lifecycleScope.launch {
//            notes.forEach { dao().insertNote(it) }
//            categories.forEach { dao().insertCategory(it) }
//            tasks.forEach { dao().insertTask(it) }
//        }
    }
}
