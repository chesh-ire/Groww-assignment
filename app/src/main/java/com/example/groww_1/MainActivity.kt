package com.example.groww_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.groww_1.Theme.Groww_1Theme
import com.example.groww_1.navigation.AppNavGraph
import com.example.groww_1.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import com.example.groww_1.watchlist.WatchlistViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            Groww_1Theme(themeViewModel = themeViewModel) {
                MainScreen(themeViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val bottomScreens = listOf(Screen.Explore, Screen.Watchlist)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val watchlistViewModel: WatchlistViewModel = hiltViewModel()
    Scaffold(
        bottomBar = {
            if (currentRoute in bottomScreens.map { it.name }) {
                NavigationBar {
                    bottomScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = {},
                            label = { Text(screen.name) },
                            selected = currentRoute == screen.name,
                            onClick = {
                                navController.navigate(screen.name) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            themeViewModel = themeViewModel ,
            watchlistViewModel = watchlistViewModel
        )
    }
}
