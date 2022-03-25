package com.example.todoapp_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.database.entities.Note
import com.example.todoapp_kotlin.database.entities.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.Provider


@Database(
    entities = [
        Caterogy::class,
        Task::class,
        Note::class
    ],
    version = 1
)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun dao() : Dao

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

    abstract class callback (
        private val applicationScope: CoroutineScope
            ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                applicationScope.launch {
                    val dao = database.dao()

                    applicationScope.launch {
                        val categories = listOf(
                            Caterogy("sport"),
                            Caterogy("study"),
                            Caterogy("work"),
                        )

                        val tasks = listOf(
                            Task(0,"task 1", "task 1 description","21/03/2022","21:30","sport",0),
                            Task(1,"task 2", "task 2 description","21/03/2022","22:30","study",1),
                            Task(2,"task 3", "task 3 description", "20/03/2022","08:00","work",0),
                            Task(3,"task 4", "task 4 description","20/03/2022","10:30","sport",1),
                            Task(4,"task 5", "task 5 description","22/03/2022","15:15","work",0),
                        )

                        val notes = listOf(
                            Note(0,"note 1","sport"),
                            Note(1,"note 2","study"),
                            Note(2,"note 3","sport"),
                            Note(3,"note 4","work"),
                        )

                        tasks.forEach { dao.insertTask(it) }
                        categories.forEach { dao.insertCategory(it) }
                        notes.forEach { dao.insertNote(it) }
                    }
                }
            }
        }
    }


}