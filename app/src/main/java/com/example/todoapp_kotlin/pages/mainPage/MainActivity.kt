package com.example.todoapp_kotlin.pages.mainPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.TaskDatabase
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        supportActionBar?.hide()

        /*database creation*/
        val dao = TaskDatabase.getInstance(this).dao

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
            tasks.forEach { dao.insertTask(it) }
            categories.forEach { dao.insertCategory(it) }
            //how to get data
//            val categorywithtask = dao.getTaskAWithCategorie("sport")
//            categorywithtask.first()
        }
    }
}
