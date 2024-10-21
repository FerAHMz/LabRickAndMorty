package com.example.rickandmortylab.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.CharacterDb // Asegúrate de importar tu archivo CharacterDb
import com.example.rickandmortylab.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {

    private val characterDb = CharacterDb()

    private val _uiState = MutableStateFlow(CharacterListState())
    val uiState: StateFlow<CharacterListState> = _uiState

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val characters = characterDb.getAllCharacters()

                _uiState.update { it.copy(isLoading = false, data = characters) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }

    fun retryLoad() {
        _uiState.update { it.copy(isLoading = true, hasError = false) }
        loadCharacters()
    }

    fun setErrorState() {
        _uiState.update { it.copy(hasError = true, isLoading = false) }
    }
}
