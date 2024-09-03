package com.serpak.ip_test_task.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serpak.ip_test_task.entity.ItemEntity

import com.serpak.ip_test_task.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemScreenViewModel @Inject constructor(
    private val itemRepository: ItemRepository
): ViewModel() {

    // Получаем LiveData из репозитория
    val items: LiveData<List<ItemEntity>> = itemRepository.getAllItems()

    init {
        // Загружаем данные при инициализации, если нужно
        fetchAllItems()
    }

    private fun fetchAllItems() {
        viewModelScope.launch {
            try {
                // Можно добавить дополнительные операции, если необходимо
                // Например, вставку или удаление данных
                // Но сами данные будут обновляться через LiveData
            } catch (e: Exception) {
                Log.e("ItemScreenViewModel", "Error fetching items", e)
            }
        }
    }
}