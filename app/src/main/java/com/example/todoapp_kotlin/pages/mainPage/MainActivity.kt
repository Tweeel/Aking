package com.example.todoapp_kotlin.pages.mainPage

import android.app.Dialog
import android.content.Intent
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

        /*setup the fab*/
        val fab = findViewById<FloatingActionButton>(R.id.fab)
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
                viewModel.insertCategory(Caterogy(new_category_dialog.findViewById<TextInputEditText>(R.id.title_text).text.toString(),color))
                new_category_dialog.dismiss()
            }
        }

//        fun dao() = TaskDatabase.getInstance(this).dao()
//        val notes = listOf(
//            Note(null,"note 1","sport"),
//            Note(null,"note 2","study"),
//            Note(null,"note 3","sport"),
//            Note(null,"note 4","work"),
//            Note(null,"note 5","work"),
//        )
//        val categories = listOf(
//            Caterogy("sport"),
//            Caterogy("study"),
//            Caterogy("work"),
//        )
//
//        val tasks = listOf(
//            Task(null,"task 1", "task 5 description","22/03/2022","15:15","work",0),
//            Task(null,"task 2", "task 5 description","22/03/2022","15:15","study",0),
//            Task(null,"task 3", "task 5 description","22/03/2022","15:15","sport",0),
//            Task(null,"task 4", "task 5 description","22/03/2022","15:15","study",0),
//            Task(null,"task 5", "task 5 description","22/03/2022","15:15","work",0)
//        )
//
//        lifecycleScope.launch {
//            notes.forEach { dao().insertNote(it) }
//            categories.forEach { dao().insertCategory(it) }
//            tasks.forEach { dao().insertTask(it) }
//        }
    }
}
