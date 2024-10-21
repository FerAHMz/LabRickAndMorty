package com.example.rickandmortylab.locations

import com.example.rickandmortylab.model.Location

data class LocationDetailState(
    val isLoading: Boolean = true,
    val location: Location? = null,
    val hasError: Boolean = false
)


