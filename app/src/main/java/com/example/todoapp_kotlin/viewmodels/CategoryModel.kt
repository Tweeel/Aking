package com.example.todoapp_kotlin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.todoapp_kotlin.database.TaskDatabase

class CategoryModel(application : Application,category:String): AndroidViewModel(application) {
    val dao = TaskDatabase.getInstance(application).dao()
    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val tasksByCategory = dao.getTasksByDate(category).asLiveData()

}