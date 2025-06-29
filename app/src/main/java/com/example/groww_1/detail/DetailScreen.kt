package com.example.groww_1.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder


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
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = if (alreadyAdded) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (alreadyAdded) "In Watchlist" else "Add to Watchlist",
                            tint = MaterialTheme.colorScheme.onBackground
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
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Sector: ${it.sector}", color = MaterialTheme.colorScheme.onBackground)
                    Text("Industry: ${it.industry}", color = MaterialTheme.colorScheme.onBackground)
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
                        .height(300.dp)
                ) {

                    StockLineChart(
                        dataPoints = priceHistory,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                } else{
                    Text(
                        "Loading chart data...",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )

            }



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
    var customName by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                "Add to Watchlist",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (alreadyAdded) {
                Text(
                    "$symbol is already in your watchlist.",
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    "Enter watchlist name (optional):",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = customName,
                    onValueChange = { customName = it },
                    placeholder = { Text("E.g. Tech Stocks") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )

                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!alreadyAdded) {
                        onConfirmAdd(
                            WatchlistItem(
                                symbol = symbol,
                                name = if (customName.isNotBlank()) customName else stockName ?: symbol
                            )
                        )
                    }
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (alreadyAdded) "Dismiss" else "Add",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


