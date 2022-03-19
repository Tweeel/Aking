package com.example.todoapp_kotlin.mainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_kotlin.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*setup the bottomNavigationView*/
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background =null
        val navController = findNavController(R.id.fragment)
        //change the name in the actionbar with each fragment
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.tasksFragment,
                R.id.monthFragment,
                R.id.categiriesFragment,
                R.id.notesFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        //combine the navigation component with the bottom navigation view
        bottomNavigationView.setupWithNavController(navController)
    }
}