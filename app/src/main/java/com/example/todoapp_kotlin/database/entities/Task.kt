package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val idTask : Int? = null,
    val title : String,
    val description: String,
    val date : String,
    val time : String,
    val categoryName : String,
    val state : Int

)