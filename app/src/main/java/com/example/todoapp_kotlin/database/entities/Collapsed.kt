package com.example.todoapp_kotlin.database.entities

data class Collapsed (
    val title:String,
    var tasks: MutableList<Task>? = emptyList<Task>().toMutableList(),
    var collapsed: Boolean?= true
)