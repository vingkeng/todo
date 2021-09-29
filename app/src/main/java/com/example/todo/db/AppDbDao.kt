package com.example.todo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.model.TodoItem

@Dao
interface AppDbDao {

    @Query("SELECT * FROM todo_items")
    fun getAllRecords(): LiveData<List<TodoItem>>

    @Query("SELECT MAX(id) FROM todo_items")
    fun getMaxId(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(todoItem: TodoItem)

    @Query("SELECT * FROM todo_items WHERE id = :itemId")
    fun searchItemWithId(itemId: String):TodoItem

    @Query("DELETE FROM todo_items")
    fun deleteAllRecords()

    @Query("DELETE FROM todo_items WHERE id = :itemId")
    fun deleteItemWithId(itemId: String)
}