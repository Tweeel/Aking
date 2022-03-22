package com.example.todoapp_kotlin.database.entities

import androidx.room.*

@Entity
data class Caterogy(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryName")
    val categoryName : String,
)