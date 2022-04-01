package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val idTask : Int? = null,
    val title : String,
    val description: String,
    val date : String? = "Anyday",
    val time : String? = "Anytime",
    val categoryName : String? = "Uncategorized",
    val state : Int? = 0

)