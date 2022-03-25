package com.example.todoapp_kotlin.pages.mainPage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.pages.addPage.AddActivity
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
            val intent = Intent(this,AddActivity::class.java);
            startActivity(intent)
            dialog_new.dismiss()
        }
        note.setOnClickListener{ }
        list.setOnClickListener {  }

        fab.setOnClickListener {
            // Showing the dialog_new here
            dialog_new.show();
        }
    }
}
