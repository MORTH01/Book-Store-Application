package com.example.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import com.example.bookstore.SplashScreen
import androidx.compose.material3.Text
import com.example.bookstore.data.InventoryDatabase
import com.example.bookstore.data.InventoryItem
import com.example.bookstore.ui.theme.BookstoreInventoryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookstoreInventoryAppTheme {


                val context = LocalContext.current
                val database = remember { InventoryDatabase.getDatabase(context) }
                val repository = remember { InventoryRepository(database.inventoryDao()) }
                val viewModel: InventoryViewModel = viewModel(
                    factory = InventoryViewModelFactory(repository)
                )


                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {


                    composable("splash") {
                        SplashScreen(navController = navController)
                    }



                    composable("main") {
                        val itemList by viewModel.allItems.collectAsState()

                        MainScreen(
                            items = itemList,
                            onAddClick = { navController.navigate("add") },
                            onItemClick = { selectedItem ->
                                navController.navigate("edit/${selectedItem.id}")
                            }
                        )
                    }

                    composable("add") {
                        AddEditItemScreen(
                            viewModel = viewModel,
                            onSave = { navController.popBackStack() },
                            onCancel = { navController.popBackStack() }
                        )
                    }


                    composable(
                        route = "edit/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getInt("itemId")
                        var item by remember { mutableStateOf<InventoryItem?>(null) }

                        LaunchedEffect(itemId) {
                            item = itemId?.let { viewModel.getItemById(it) }
                        }

                        if (item != null) {
                            AddEditItemScreen(
                                viewModel = viewModel,
                                existingItem = item,
                                onSave = { navController.popBackStack() },
                                onCancel = { navController.popBackStack() }
                            )
                        } else {
                            Text("Loading item...")
                        }
                    }
                }
            }
        }
    }
}
