package com.example.todoapp_kotlin.pages.mainPage

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.pages.appNotePage.AddNoteActivity
import com.example.todoapp_kotlin.pages.mainPage.fragments.CategiriesFragment
import com.example.todoapp_kotlin.pages.mainPage.fragments.MonthFragment
import com.example.todoapp_kotlin.pages.mainPage.fragments.NotesFragment
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[MyViewModel::class.java]

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

        if(intent.getStringExtra("note")!=null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, NotesFragment()).commit()
            bottomNavigationView.selectedItemId = R.id.notesFragment
        }

        if(intent.getStringExtra("category")!=null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, CategiriesFragment()).commit()
            bottomNavigationView.selectedItemId = R.id.categiriesFragment
        }

        /*setup the fab*/
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        //get the drawable

        //change the fab icon color
        val myFabSrc = resources.getDrawable(R.drawable.add)
        myFabSrc.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        fab.setImageDrawable(myFabSrc)

        val dialog_new = Dialog(this)
        dialog_new.setContentView(R.layout.create_new)
        dialog_new.window?.setBackgroundDrawable(getDrawable(R.drawable.back_round_white))

        val new_category_dialog = Dialog(this)
        new_category_dialog.setContentView(R.layout.new_category)
        new_category_dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.back_round_white))

        val task = dialog_new.findViewById<TextView>(R.id.task)
        val note = dialog_new.findViewById<TextView>(R.id.note)
        val list = dialog_new.findViewById<TextView>(R.id.list)

        val category = new_category_dialog.findViewById<AppCompatButton>(R.id.add)

        task.setOnClickListener {
            startActivity(Intent(this,AddTaskActivity::class.java))
            dialog_new.dismiss()
        }
        note.setOnClickListener{
            startActivity(Intent(this,AddNoteActivity::class.java))
            dialog_new.dismiss()
        }
        list.setOnClickListener {
            new_category_dialog.findViewById<RadioGroup>(R.id.colors).check(R.id.blue)
            new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text = null
            new_category_dialog.show()
            dialog_new.dismiss()
        }

        fab.setOnClickListener {
            dialog_new.show()
        }

        category.setOnClickListener {
            if(new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString().isNotEmpty()){
                lateinit var color : String
                when(new_category_dialog.findViewById<RadioGroup>(R.id.colors).checkedRadioButtonId){
                    R.id.blue -> color ="blue"
                    R.id.pink -> color ="pink"
                    R.id.green -> color ="green"
                    R.id.purple -> color ="purple"
                    R.id.beige -> color ="beige"
                }
                viewModel.insertCategory(Caterogy(categoryName=new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString(),color=color))
                new_category_dialog.dismiss()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, CategiriesFragment()).commit()
                bottomNavigationView.selectedItemId = R.id.categiriesFragment
            }
        }
    }
}
