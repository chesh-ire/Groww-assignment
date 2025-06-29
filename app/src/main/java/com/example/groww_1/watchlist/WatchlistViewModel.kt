package com.example.groww_1.watchlist

//package com.example.groww_1.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class WatchlistItem(val symbol: String, val name: String)

class WatchlistViewModel : ViewModel() {

    private val _watchlist = MutableStateFlow<List<WatchlistItem>>(emptyList())
    val watchlist: StateFlow<List<WatchlistItem>> = _watchlist

    fun addToWatchlist(item: WatchlistItem) {
        _watchlist.update { current ->
            if (current.any { it.symbol == item.symbol }) current
            else current + item
        }
    }

    fun removeFromWatchlist(symbol: String) {
        _watchlist.update { current -> current.filterNot { it.symbol == symbol } }
    }
}
