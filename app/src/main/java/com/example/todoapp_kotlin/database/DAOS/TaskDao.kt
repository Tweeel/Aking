package com.example.todoapp_kotlin.database.DAOS

import androidx.room.*
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.database.entities.relations.CategoryAndNote
import com.example.todoapp_kotlin.database.entities.relations.CategoryAndTask

@Dao
interface TaskDao {
    /*insert*/
    @Insert
    suspend fun insertTask(task : Task)
    @Insert
    suspend fun insertNote(note : Note)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  insertCategory(category : Caterogy)

    /*get all*/
    @Query("SELECT * FROM Task")
    suspend fun getTasks() : List<Task>

    @Query("SELECT * FROM Note")
    suspend fun getNotes() : List<Note>

    @Query("SELECT * FROM Caterogy")
    suspend fun getCategories() : List<Caterogy>

    /*get all with category*/
    @Transaction
    @Query("SELECT * FROM Task WHERE categoryName = :categorieName")
    suspend fun getTaskAWithCategorie (categorieName : String) : List<CategoryAndTask>

    @Transaction
    @Query("SELECT * FROM Note WHERE categoryName = :categorieName")
    suspend fun getNoteAWithCategory (categorieName : String) : List<CategoryAndNote>

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
    suspend fun getTasksDesc() : List<Task>
}