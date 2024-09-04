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
) : ViewModel() {


    val items: LiveData<List<ItemEntity>> = itemRepository.getAllItems()


    fun deleteItemById(id: Int) {
        viewModelScope.launch {
            try {
                itemRepository.deleteById(id)
            } catch (e: Exception) {
                Log.e("ItemScreenViewModel", "Ошибка при удалении", e)
            }
        }
    }

    fun updateItemAmount(itemId:Int, newAmount:Int){
        viewModelScope.launch{
            try{
                itemRepository.updateItemAmount(itemId, newAmount)
            }catch(e: Exception){
                Log.e("ItemScreenViewModel", "Ошибка при обновлении данных", e)
            }
        }
    }

}