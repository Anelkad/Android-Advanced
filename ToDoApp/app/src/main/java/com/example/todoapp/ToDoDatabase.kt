package com.example.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoItem::class), version = 1, exportSchema = false)
public abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}
