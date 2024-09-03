package com.serpak.ip_test_task.repository

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serpak.ip_test_task.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun insertItem(entity: ItemEntity)
    fun getAllItems():LiveData<List<ItemEntity>>
    suspend fun deleteById(itemId:Int)
}