package com.fenix.todoapp.data.dp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoitems")
    suspend fun getList(): List<TodoItemEntity>
    @Query("SELECT * FROM todoitems WHERE id = :id")
    suspend fun getItem(id: String) : TodoItemEntity
    @Upsert
    suspend fun upsertItem(item : TodoItemEntity)
    @Upsert
    suspend fun upsertItem(item : List<TodoItemEntity>)
    @Query("DELETE  FROM todoitems WHERE id = :id")
    suspend fun deleteItem(id : String)
}