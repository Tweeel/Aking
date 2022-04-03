package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp_kotlin.database.Dao
import com.example.todoapp_kotlin.database.TaskDatabase
import com.example.todoapp_kotlin.pages.mainPage.MainActivity

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val idTask : Int? = null,
    val title : String,
    val description: String,
    val date : String? = "Anyday",
    val time : String? = "Anytime",
    val categoryId : Int? = 1,
    val categoryName : String? = "Uncategorized",
    val categoryColor : String? = "black",
    var state : Int? = 0
)