package com.example.todoapp_kotlin.mainPage

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.tasksFragment,
            R.id.monthFragment,
            R.id.categiriesFragment,
            R.id.notesFragment
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        supportActionBar?.hide()
//        /*setup the actionbar*/
//        //set the custom layout
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        toolbar.inflateMenu(R.menu.toolbar_menu)
//        val title = toolbar.findViewById<TextView>(R.id.toolbar_title)
//        //change the name in the actionbar depends on each fragment
//        bottomNavigationView.setOnItemSelectedListener{
//            when(it.itemId){
//                R.id.tasksFragment -> title.text = "Tasks"
//                R.id.monthFragment -> title.text = "Calender"
//                R.id.categiriesFragment -> title.text = "Categories"
//                R.id.notesFragment -> title.text = "Notes"
//                else -> title.text = "Tasks"
//            }
//            true
//        }
    }
}
