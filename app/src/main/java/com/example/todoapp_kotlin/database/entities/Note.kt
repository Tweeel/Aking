package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val idTile : Int,
    val title : String,
    val description : String,
    val caterogieName : String
)