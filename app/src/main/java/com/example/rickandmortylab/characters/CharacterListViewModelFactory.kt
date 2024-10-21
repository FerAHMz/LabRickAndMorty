package com.example.rickandmortylab.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CharacterListViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
            "Unknown ViewModel class"
        }
        return CharacterListViewModel() as T
    }
}
