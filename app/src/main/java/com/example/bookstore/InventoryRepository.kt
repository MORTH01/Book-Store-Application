package com.example.bookstore

import com.example.bookstore.data.InventoryDao
import com.example.bookstore.data.InventoryItem
import kotlinx.coroutines.flow.Flow
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import androidx.core.content.FileProvider

suspend fun exportToCsv(items: List<InventoryItem>, context: Context): Uri? {
    return withContext(Dispatchers.IO) {
        val fileName = "book_inventory.csv"
        val csvHeader = "Name,Category,Quantity,Price\n"
        val csvBody = items.joinToString(separator = "\n") {
            "${it.name},${it.category},${it.quantity},${it.price}"
        }

        try {
            val file = File(context.cacheDir, fileName)
            file.writeText(csvHeader + csvBody)

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}

class InventoryRepository(private val dao: InventoryDao) {
    companion object {
        fun exportToCsv(items: List<InventoryItem>, context: Context): Uri? {
            val fileName = "book_inventory.csv"
            val csvHeader = "Name,Category,Quantity,Price\n"
            val csvBody = items.joinToString(separator = "\n") {
                "${it.name},${it.category},${it.quantity},${it.price}"
        }
            return try {
                val file = File(context.cacheDir, fileName)
                file.writeText(csvHeader + csvBody)
                FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    val allItems: Flow<List<InventoryItem>> = dao.getAllItems()

    suspend fun insert(item: InventoryItem) {
        dao.insertItem(item)
    }

    suspend fun update(item: InventoryItem) {
        dao.updateItem(item)
    }

    suspend fun delete(item: InventoryItem) {
        dao.deleteItem(item)
    }

    suspend fun getItemById(id: Int): InventoryItem? {
        return dao.getItemById(id)
    }
}