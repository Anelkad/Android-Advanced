package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.models.ToDoItem

@Database(entities = arrayOf(ToDoItem::class), version = 1, exportSchema = false)
public abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}
