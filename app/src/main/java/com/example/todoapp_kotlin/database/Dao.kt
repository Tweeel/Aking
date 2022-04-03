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
    @Query("SELECT * FROM Task ORDER BY date ASC")
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

    @Query("DELETE FROM Task WHERE categoryName =:category")
    suspend fun deleteTasksByCategoryName(category: String)

    /*specifics queries*/
    @Query("SELECT * FROM Task WHERE state = 1")
    fun getCompeletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE state = 0")
    fun getIncompeletedTasks(): Flow<List<Task>>

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
    @Query("SELECT * FROM Task WHERE categoryId=:category")
    fun getTasksByCategoryName(category: Int) : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date =:date ORDER BY time ASC")
    fun getTasksByDate(date: String) : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date =:date OR date='Anyday' ORDER BY time ASC")
    fun getTodayTasks(date: String) : Flow<List<Task>>

    /*get category*/
    @Query("SELECT categoryName FROM Caterogy WHERE idCategory=:id")
    fun getCategoryNameById(id:Int) : String

    @Query("SELECT color FROM Caterogy WHERE idCategory=:id")
    fun getCategoryColorById(id:Int) : String
}