package com.example.rickandmortylab.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortylab.data.LocationDao
import com.example.rickandmortylab.RickAndMortyApiClient

class LocationListViewModelFactory(
    private val apiClient: RickAndMortyApiClient,
    private val locationDao: LocationDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationListViewModel::class.java)) {
            return LocationListViewModel(apiClient, locationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
