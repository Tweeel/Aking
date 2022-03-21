package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Caterogy(
    @PrimaryKey(autoGenerate = false)
    val categorieName : String,
    val color : String
)