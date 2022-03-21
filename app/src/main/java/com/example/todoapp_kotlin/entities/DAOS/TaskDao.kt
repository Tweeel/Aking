package com.example.todoapp_kotlin.entities.DAOS

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.todoapp_kotlin.entities.Caterogie
import com.example.todoapp_kotlin.entities.Task
import com.example.todoapp_kotlin.entities.relations.CategorieAndTask

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task : Task)
    @Insert
    suspend fun  insertCategorie(categorie : Caterogie)

    @Transaction
    @Query("SELECT * FROM Task WHERE caterogie = :categorieName")
    suspend fun getTaskAndCategorieWithCategorieName (categorieName : String) : List<CategorieAndTask>
}