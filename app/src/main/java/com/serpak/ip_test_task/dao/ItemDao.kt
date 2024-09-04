package com.serpak.ip_test_task.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serpak.ip_test_task.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(entity: ItemEntity)
    @Query("SELECT * FROM item")
    fun getAllItems(): LiveData<List<ItemEntity>>

    @Query("DELETE FROM item WHERE id = :itemId")
    suspend fun deleteById(itemId:Int)

    @Query("UPDATE item SET amount = :newAmount WHERE id = :itemId")
    suspend fun updateItemAmount(itemId: Int, newAmount: Int)

}