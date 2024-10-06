package com.example.rickandmortylab.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CharacterDetailState())
    val uiState: StateFlow<CharacterDetailState> = _uiState

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailState(isLoading = true)
            try {
                delay(2000)
                val character = CharacterDb().getCharacterById(characterId)
                _uiState.value = CharacterDetailState(isLoading = false, character = character)
            } catch (e: Exception) {
                _uiState.value = CharacterDetailState(isLoading = false, hasError = true)
            }
        }
    }

    fun retryLoad(characterId: Int) {
        _uiState.value = CharacterDetailState(isLoading = true)
        loadCharacter(characterId)
    }

    fun setErrorState() {
        _uiState.value = _uiState.value.copy(hasError = true, isLoading = false)
    }
}





