package com.serpak.ip_test_task.repository

import androidx.lifecycle.LiveData
import com.serpak.ip_test_task.dao.ItemDao
import com.serpak.ip_test_task.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class ItemRepoImpl(
    private val dao: ItemDao
): ItemRepository {
    override suspend fun insertItem(entity: ItemEntity) {
        dao.insertItem(entity)
    }

    override fun getAllItems(): LiveData<List<ItemEntity>> {
      return dao.getAllItems()
    }

    override suspend fun deleteById(itemId: Int) {
        dao.deleteById(itemId)
    }
}