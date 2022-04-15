package com.example.todoapp_kotlin.database.entities

data class Collapsed (
    val title:String,
    var tasks: List<Task>,
    var collapsed: Boolean?= true
)