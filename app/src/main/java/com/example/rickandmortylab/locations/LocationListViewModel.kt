package com.example.rickandmortylab.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationListState(isLoading = true)
            try {
                delay(4000)
                if (_uiState.value.hasError) return@launch
                val locations = LocationDb().getAllLocations()
                _uiState.value = LocationListState(isLoading = false, data = locations)
            } catch (e: Exception) {
                _uiState.value = LocationListState(isLoading = false, hasError = true)
            }
        }
    }

    fun retryLoad() {
        _uiState.value = _uiState.value.copy(isLoading = true, hasError = false)
        loadLocations()
    }

    fun setErrorState() {
        _uiState.value = _uiState.value.copy(hasError = true, isLoading = false)
    }
}





