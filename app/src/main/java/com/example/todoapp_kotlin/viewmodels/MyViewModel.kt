package com.example.todoapp_kotlin.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp_kotlin.database.TaskDatabase
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MyViewModel(application : Application): AndroidViewModel(application) {

    var caterogy  = MutableStateFlow("")
    var date  = MutableStateFlow("")

    val tasksByCategory = caterogy.flatMapLatest {
        dao.getTasksByCategoryName(it.toInt())
    }

    val tasksByDate = date.flatMapLatest {
        dao.getTasksByDate(it)
    }

    val dao = TaskDatabase.getInstance(application).dao()
    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val allTasks : LiveData<List<Task>> = dao.getTasks().asLiveData()
    val allNotes : LiveData<List<Note>> = dao.getNotes().asLiveData()
    val allCategories : LiveData<List<Caterogy>> = dao.getCategories().asLiveData()

    /*insert*/
    fun insertTask(task : Task){
        viewModelScope.launch {
            dao.insertTask(task)
        }
    }

    fun insertNote(note : Note){
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }

    fun insertCategory(caterogy: Caterogy){
        viewModelScope.launch {
            dao.insertCategory(caterogy)
        }
    }

    /*update*/
    fun updateTask(task: Task){
        viewModelScope.launch {
            dao.updateTask(task)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }

    fun updateCaterogy(caterogy: Caterogy){
        viewModelScope.launch {
            dao.updateCaterogy(caterogy)
        }
    }

    /*delete*/
    fun deleteTask(task: Task){
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            dao.deleteNote(note)
        }
    }

    fun deleteCaterogy(caterogy: Caterogy){
        viewModelScope.launch {
            dao.deleteCaterogy(caterogy)
        }
    }

    fun deleteTasksByCategoryName(category: String){
        viewModelScope.launch {
            dao.deleteTasksByCategoryName(category)
        }
    }
}