package com.example.todoapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_table")
    fun getAll(): Flow<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: ToDoItem)

    @Query("DELETE FROM to_do_table WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)
}