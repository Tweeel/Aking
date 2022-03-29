package com.example.todoapp_kotlin.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Caterogy(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryName")
    val categoryName : String
)