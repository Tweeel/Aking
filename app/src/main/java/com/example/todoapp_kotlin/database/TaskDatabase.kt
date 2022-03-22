package com.example.todoapp_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task


@Database(
    entities = [
        Caterogy::class,
        Task::class,
        Note::class
    ],
    version = 1
)
abstract class TaskDatabase : RoomDatabase(){

    abstract  val dao : Dao

    companion object {
        @Volatile
        private var INSTANCE : TaskDatabase? = null

        fun getInstance(context : Context) : TaskDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}