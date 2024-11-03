package com.example.rickandmortylab.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.LocationDao
import com.example.rickandmortylab.data.LocationEntity
import com.example.rickandmortylab.model.Location
import com.example.rickandmortylab.RickAndMortyApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationListViewModel(
    private val apiClient: RickAndMortyApiClient,
    private val locationDao: LocationDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val locations = apiClient.getLocations()
                val locationEntities = locations.map {
                    LocationEntity(
                        it.id,
                        it.name,
                        it.type ?: "Unknown",
                        it.dimension ?: "Unknown"
                    )
                }

                withContext(Dispatchers.IO) {
                    locationDao.insertLocations(locationEntities)
                }

                _uiState.update {
                    it.copy(isLoading = false, data = locationEntities.map { entity ->
                        Location(entity.id, entity.name, entity.type, entity.dimension)
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()

                val localLocations = withContext(Dispatchers.IO) {
                    locationDao.getAllLocations()
                }
                if (localLocations.isNotEmpty()) {
                    _uiState.update {
                        it.copy(isLoading = false, data = localLocations.map { entity ->
                            Location(entity.id, entity.name, entity.type, entity.dimension)
                        })
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, hasError = true) }
                }
            }
        }
    }

    fun retryLoad() {
        _uiState.update { it.copy(isLoading = true, hasError = false) }
        loadLocations()
    }

    fun setErrorState() {
        _uiState.update { it.copy(hasError = true, isLoading = false) }
    }
}
