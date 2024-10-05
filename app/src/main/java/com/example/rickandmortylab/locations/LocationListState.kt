package com.example.rickandmortylab.locations


import com.example.rickandmortylab.model.Location

data class LocationListState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)
