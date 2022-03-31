package com.example.todoapp_kotlin.database

import androidx.room.*
import androidx.room.Dao
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM Note ORDER BY idTile DESC")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM Caterogy")
    fun getCategories() : Flow<List<Caterogy>>

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

    /*get where */
    @Query("SELECT * FROM Task WHERE categoryName =:category")
    fun getTasksByCategoryName(category: String) : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date =:date")
    fun getTasksByDate(date: String) : Flow<List<Task>>
}