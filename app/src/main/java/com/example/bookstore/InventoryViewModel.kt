package com.example.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.InventoryItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val repository: InventoryRepository
) : ViewModel() {

    // Expose the list of all items as StateFlow
    val allItems: StateFlow<List<InventoryItem>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Insert a new item
    fun insert(item: InventoryItem) = viewModelScope.launch {
        repository.insert(item)
    }

    // Update an existing item
    fun update(item: InventoryItem) = viewModelScope.launch {
        repository.update(item)
    }

    // Delete an item
    fun delete(item: InventoryItem) = viewModelScope.launch {
        repository.delete(item)
    }

    // Get item by ID (for detail or edit screen)
    suspend fun getItemById(id: Int): InventoryItem? {
        return repository.getItemById(id)
    }
}

// ViewModel Factory to create InventoryViewModel with a repository
class InventoryViewModelFactory(
    private val repository: InventoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
