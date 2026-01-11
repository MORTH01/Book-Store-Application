package com.example.bookstore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bookstore.data.InventoryItem
import androidx.compose.material3.Icon
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.clickable
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import android.widget.Toast
import com.example.bookstore.InventoryRepository.Companion.exportToCsv


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    items: List<InventoryItem>,
    onAddClick: () -> Unit,
    onItemClick: (InventoryItem) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = items.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "\uD83D\uDCDA Bookstore Inventory") },
                actions = {
                    val scope = rememberCoroutineScope()

                    IconButton(onClick = {
                        scope.launch {
                            val uri = exportToCsv(items, context)
                            if (uri != null) {
                                Toast.makeText(context, "CSV file exported to cache.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to export CSV.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Download, contentDescription = "Export to CSV")
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text(text = "+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            val totalQuantity = filteredItems.sumOf { it.quantity }
            val totalPrice = filteredItems.sumOf { it.price }
            val totalBooks = filteredItems.size

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total Books: $totalBooks", style = MaterialTheme.typography.bodyMedium)
                    Text("Total Quantity: $totalQuantity", style = MaterialTheme.typography.bodyMedium)
                    Text("Total Price: RM %.2f".format(totalPrice), style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No inventory items available.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredItems) { item ->
                        InventoryItemRow(item = item, onClick = { onItemClick(item) })
                    }
                }
            }
        }
    }
}
@Composable
fun InventoryItemRow(item: InventoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: ${item.name}", style = MaterialTheme.typography.titleMedium)
            Text("Category: ${item.category}")
            Text("Quantity: ${item.quantity}")
            Text("Price: RM ${item.price}")
        }
    }
}
