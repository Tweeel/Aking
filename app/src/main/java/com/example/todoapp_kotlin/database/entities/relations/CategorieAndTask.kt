package com.example.todoapp_kotlin.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Task

data class CategorieAndTask (
    @Embedded val categorie: Caterogy,
    @Relation(
        parentColumn = "caterogieName",
        entityColumn = "caterogieName"
    )
    val task : List<Task>
)