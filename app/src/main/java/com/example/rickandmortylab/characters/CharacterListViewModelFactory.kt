package com.example.rickandmortylab.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortylab.data.CharacterDao
import com.example.rickandmortylab.RickAndMortyApiClient

class CharacterListViewModelFactory(
    private val apiClient: RickAndMortyApiClient,
    private val characterDao: CharacterDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterListViewModel::class.java) -> {
                CharacterListViewModel(apiClient, characterDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
