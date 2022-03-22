package com.example.todoapp_kotlin.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Task

data class CategoryAndTask (
    @Embedded val category: Caterogy,
    @Relation(
        parentColumn = "categoryName",
        entityColumn = "categoryName"
    )
    val task : List<Task>
)