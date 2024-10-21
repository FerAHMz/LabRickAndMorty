package com.example.rickandmortylab.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailState())
    val uiState: StateFlow<LocationDetailState> = _uiState

    fun loadLocation(locationId: Int) {
        viewModelScope.launch {
            _uiState.value = LocationDetailState(isLoading = true)
            try {
                delay(2000)
                val location = LocationDb().getLocationById(locationId)
                _uiState.value = LocationDetailState(isLoading = false, location = location)
            } catch (e: Exception) {
                _uiState.value = LocationDetailState(isLoading = false, hasError = true)
            }
        }
    }

    fun retryLoad(locationId: Int) {
        _uiState.value = LocationDetailState(isLoading = true)
        loadLocation(locationId)
    }

    fun setErrorState() {
        _uiState.value = _uiState.value.copy(hasError = true, isLoading = false)
    }
}




