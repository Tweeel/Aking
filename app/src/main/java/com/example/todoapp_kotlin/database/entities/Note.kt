package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val idTile : Int? = null,
    val text : String,
    val color : String,
    val date : String,
    val version : Int? = 1

)