package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.models.Status
import com.example.todoapp.models.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_table")
    fun getAll(): Flow<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: ToDoItem)

    @Query("DELETE FROM to_do_table WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)

    @Query("UPDATE to_do_table SET title = :title, status = :status WHERE id = :itemId")
    fun updateItem(itemId: Int, title: String, status: Status)
}