package com.example.groww_1.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groww_1.chart.StockLineChart
import com.example.groww_1.watchlist.WatchlistItem
import com.example.groww_1.watchlist.WatchlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    symbol: String,
    viewModel: DetailViewModel = hiltViewModel(),
    watchlistViewModel: WatchlistViewModel = hiltViewModel()
) {
    val stock by viewModel.stock.collectAsState()
    val priceHistory by viewModel.priceHistory.collectAsState()
    val watchlist by watchlistViewModel.watchlist.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val alreadyAdded = watchlist.any { it.symbol == symbol }

    LaunchedEffect(symbol) {
        viewModel.loadStockDetails(symbol)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "$symbol Overview",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Add to Watchlist",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                stock?.let {
                    Text(
                        "Name: ${it.name}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Sector: ${it.sector}", color = Color.White)
                    Text("Industry: ${it.industry}", color = Color.White)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (priceHistory.isNotEmpty()) {
                    Text(
                        "Price History (Last 10 days)",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            //.padding(top = 8.dp)
                            //.background(Color.White)
                    ) {
                        StockLineChart(dataPoints = priceHistory)
                    }
                } else {
                    Text("Loading chart data...", color = MaterialTheme.colorScheme.background)
                }
            }

            Button(
                onClick = { showBottomSheet = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5EF38F))
            ) {
                Text("Add to Watchlist", color = MaterialTheme.colorScheme.background)
            }
        }
    }

    if (showBottomSheet) {
        WatchlistBottomSheet(
            symbol = symbol,
            stockName = stock?.name,
            alreadyAdded = alreadyAdded,
            onDismiss = { showBottomSheet = false },
            onConfirmAdd = { item ->
                if (!alreadyAdded) {
                    watchlistViewModel.addToWatchlist(item)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistBottomSheet(
    symbol: String,
    stockName: String?,
    alreadyAdded: Boolean,
    onDismiss: () -> Unit,
    onConfirmAdd: (WatchlistItem) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
        ) {
            Text("Add to Watchlist", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            if (alreadyAdded) {
                Text("$symbol is already in your watchlist.")
            } else {
                Text("Do you want to add $symbol to your watchlist?")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!alreadyAdded) {
                        onConfirmAdd(
                            WatchlistItem(symbol = symbol, name = stockName ?: symbol)
                        )
                    }
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (alreadyAdded) "Dismiss" else "Add")
            }
        }
    }
}
