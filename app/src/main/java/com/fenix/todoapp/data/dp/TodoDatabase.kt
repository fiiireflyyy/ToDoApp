package com.fenix.todoapp.data.dp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoItemEntity::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao
}