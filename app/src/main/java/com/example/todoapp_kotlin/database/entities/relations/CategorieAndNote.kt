package com.example.todoapp_kotlin.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note

data class CategorieAndNote (
    @Embedded val categorie: Caterogy,
    @Relation(
        parentColumn = "caterogieName",
        entityColumn = "caterogieName"
    )
    val note : List<Note>
)