package com.example.todoapp_kotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.database.entities.relations.CategoryAndNote
import com.example.todoapp_kotlin.database.entities.relations.CategoryAndTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface Dao {
    /*insert*/
    @Insert
    suspend fun insertTask(task : Task)
    @Insert
    suspend fun insertNote(note : Note)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  insertCategory(category : Caterogy)

    /*get all*/
    @Query("SELECT * FROM Task")
    fun getTasks() : Flow<List<Task>>

    @Query("SELECT * FROM Note")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM Caterogy")
    fun getCategories() : Flow<List<Caterogy>>

    /*get all with category*/
    @Transaction
    @Query("SELECT * FROM Task WHERE categoryName = :categorieName")
    fun getTaskAWithCategorie (categorieName : String) : Flow<List<CategoryAndTask>>

    @Transaction
    @Query("SELECT * FROM Note WHERE categoryName = :categorieName")
    fun getNoteAWithCategory (categorieName : String) : Flow<List<CategoryAndNote>>

    /*Update*/
    @Update
    suspend fun updateTask(task : Task)

    @Update
    suspend fun updateNote(note : Note)

    @Update
    suspend fun updateCaterogy(category : Caterogy)

    /*delete*/
    @Delete
    suspend fun deleteTask(task : Task)

    @Delete
    suspend fun deleteNote(note : Note)

    @Delete
    suspend fun deleteCaterogy(category : Caterogy)

    /*specifics queries*/
//    @Query("SELECT * FROM Task WHERE state = 1")
//    suspend fun getCompeletedTasks()
//
//    @Query("SELECT * FROM Task WHERE state = 0")
//    suspend fun getIncompeletedTasks()

    @Query("SELECT * FROM Task ORDER BY date DESC")
    fun getTasksDesc() : Flow<List<Task>>

    /*delete all*/
    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM Caterogy")
    suspend fun deleteAllCaterogies()
}