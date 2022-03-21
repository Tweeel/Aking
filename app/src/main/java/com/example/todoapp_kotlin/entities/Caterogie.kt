package com.example.todoapp_kotlin.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Caterogie(
    @PrimaryKey(autoGenerate = false)
    val categorie_name : String
)