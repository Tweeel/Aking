package com.example.todoapp_kotlin.viewmodels

import androidx.lifecycle.*
import com.example.todoapp_kotlin.database.Dao
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(private val dao : Dao): ViewModel() {
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
}

class TaskViewModelFactory(
    private val taskDao: Dao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return  TaskViewModel(taskDao) as T
        }
        throw  IllegalAccessException("Unkown Viewmodel Class")
    }

}