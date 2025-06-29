package com.example.groww_1

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
//@HiltViewModel
class ThemeViewModel : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false) // default is light
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }
}
