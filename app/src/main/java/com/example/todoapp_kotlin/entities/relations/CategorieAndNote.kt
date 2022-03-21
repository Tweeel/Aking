package com.example.todoapp_kotlin.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp_kotlin.entities.Caterogie
import com.example.todoapp_kotlin.entities.Note
import com.example.todoapp_kotlin.entities.Task

data class CategorieAndNote (
    @Embedded val categorie: Caterogie,
    @Relation(
        parentColumn = "categorie_name",
        entityColumn = "categorie_name"
    )
    val note : Note
)