package com.example.todoapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "to_do_table")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "status")  var status: Status = Status.PENDING
): Parcelable
{
    enum class Status {
        COMPLETED, IN_PROGRESS, PENDING;
    }
}
