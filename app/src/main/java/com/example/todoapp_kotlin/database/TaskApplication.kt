package com.example.todoapp_kotlin.database

import android.app.Application

class TaskApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: TaskDatabase by lazy { TaskDatabase.getInstance(this) }
}