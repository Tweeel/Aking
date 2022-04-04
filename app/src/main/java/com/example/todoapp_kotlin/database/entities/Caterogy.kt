package com.example.todoapp_kotlin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Caterogy(
    @PrimaryKey(autoGenerate = true)
    val idCategory : Int? = null,
    val categoryName : String? = "Uncategories",
    val color: String,
    val tasksNumber: Int? = 0
)