package com.example.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.model.TodoItem

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDbDao(): AppDbDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDbInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "APP_DB"
                    )
                        .allowMainThreadQueries()
                        .build()
            }

            return INSTANCE!!
        }
    }
}