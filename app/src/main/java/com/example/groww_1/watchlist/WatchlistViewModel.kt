package com.example.groww_1.watchlist



import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class WatchlistItem(val symbol: String, val name: String)
@HiltViewModel
class WatchlistViewModel @Inject constructor() : ViewModel() {

    private val _watchlist = MutableStateFlow<List<WatchlistItem>>(emptyList())
    val watchlist: StateFlow<List<WatchlistItem>> = _watchlist

    fun addToWatchlist(item: WatchlistItem) {
        _watchlist.update { current ->
            if (current.any { it.symbol == item.symbol }) current
            else current + item
        }
    }


}
