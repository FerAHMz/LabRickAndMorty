package com.example.rickandmortylab.characterdetails

import com.example.rickandmortylab.model.Character

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val character: Character? = null,
    val hasError: Boolean = false
)

