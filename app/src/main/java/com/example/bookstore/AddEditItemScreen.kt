package com.example.bookstore

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.bookstore.data.InventoryItem
import androidx.compose.ui.text.input.KeyboardType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    viewModel: InventoryViewModel,
    existingItem: InventoryItem? = null,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(existingItem?.name ?: "") }
    var category by remember { mutableStateOf(existingItem?.category ?: "") }
    var quantity by remember { mutableStateOf((existingItem?.quantity ?: 0).toString()) }
    var price by remember { mutableStateOf((existingItem?.price ?: 0.0).toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (existingItem == null) "Add Item" else "Edit Item")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price (RM)") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onCancel) {
                    Text("Cancel")
                }
                Button(onClick = {
                    if (name.isNotBlank() && category.isNotBlank()
                        && quantity.toIntOrNull() != null && price.toDoubleOrNull() != null) {

                        val item = InventoryItem(
                            id = existingItem?.id ?: 0,
                            name = name,
                            category = category,
                            quantity = quantity.toInt(),
                            price = price.toDouble()
                        )

                        if (existingItem == null) {
                            viewModel.insert(item)
                        } else {
                            viewModel.update(item)
                        }

                        onSave()
                    }

                }) {
                    Text("Save")
                }
            }


            if (existingItem != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.delete(existingItem)
                        onSave()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    }
}
