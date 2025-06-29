package com.example.groww_1.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.groww_1.ThemeViewModel
import com.example.groww_1.allStocks.AllGainersScreen
import com.example.groww_1.allStocks.AllLosersScreen
import com.example.groww_1.detail.DetailScreen
import com.example.groww_1.watchlist.WatchlistViewModel
import com.example.groww_1.explore.ExploreScreen
import com.example.groww_1.watchlist.WatchlistScreen

enum class Screen {
    Explore,
    Watchlist
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    watchlistViewModel: WatchlistViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Explore.name,
        modifier = modifier
    ) {
        composable(Screen.Explore.name) {

            ExploreScreen(
                navController = navController,
                themeViewModel = themeViewModel
            )
        }
        composable(Screen.Watchlist.name) {
            WatchlistScreen(
                navController = navController,
                viewModel = watchlistViewModel
            )
        }
        composable("details/{symbol}") { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: return@composable
            DetailScreen(
                symbol = symbol,
                watchlistViewModel = watchlistViewModel
            )
        }
        composable("all_gainers") {
            AllGainersScreen(navController)
        }
        composable("all_losers") {
            AllLosersScreen(navController)
        }

    }
}
