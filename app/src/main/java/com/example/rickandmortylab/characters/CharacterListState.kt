package com.example.rickandmortylab.characters

data class CharacterListState(
    val isLoading: Boolean = true,
    val data: List<com.example.rickandmortylab.model.Character> = emptyList(),
    val hasError: Boolean = false
)

