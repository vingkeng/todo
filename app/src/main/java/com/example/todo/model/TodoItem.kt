package com.example.todo.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_items")
@Parcelize
data class TodoItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") @Transient val key: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "completed") var completed: Boolean = false,
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "userID") var userID: String = "",
    @ColumnInfo(name = "mCleared") var mCleared: Boolean = false
) : Parcelable
