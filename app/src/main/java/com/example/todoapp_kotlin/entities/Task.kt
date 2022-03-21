package com.example.todoapp_kotlin.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val date : String,
    val time : String,
    val caterogie: String

)