package com.example.todoapp_kotlin.database.entities

data class Parent (
    val title:String,
    var tasks: List<Task>
    )