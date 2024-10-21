package com.example.rickandmortylab.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortylab.data.CharacterDao

class CharacterDetailViewModelFactory(
    private val characterDao: CharacterDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CharacterDetailViewModel::class.java) -> {
                CharacterDetailViewModel(characterDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
