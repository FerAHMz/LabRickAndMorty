package com.example.rickandmortylab.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CharacterListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterListState())
    val uiState: StateFlow<CharacterListState> = _uiState

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = CharacterListState(isLoading = true)
            try {
                delay(4000)
                val characters = CharacterDb().getAllCharacters()
                _uiState.value = CharacterListState(isLoading = false, data = characters)
            } catch (e: Exception) {
                _uiState.value = CharacterListState(isLoading = false, hasError = true)
            }
        }
    }

    fun retryLoad() {
        _uiState.value = _uiState.value.copy(isLoading = true, hasError = false)
        loadCharacters()
    }

    fun setErrorState() {
        _uiState.value = _uiState.value.copy(hasError = true, isLoading = false)
    }
}


