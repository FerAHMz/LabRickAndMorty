package com.example.rickandmortylab.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.CharacterDao
import com.example.rickandmortylab.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val characterDao: CharacterDao) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailState())
    val uiState: StateFlow<CharacterDetailState> = _uiState

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiState.value = CharacterDetailState(isLoading = true)
            try {
                val characterEntity = characterDao.getCharacterById(characterId)

                if (characterEntity == null) {
                    _uiState.value = CharacterDetailState(hasError = true)
                } else {
                    val character = Character(
                        id = characterEntity.id,
                        name = characterEntity.name,
                        status = characterEntity.status,
                        species = characterEntity.species,
                        gender = characterEntity.gender,
                        image = characterEntity.image
                    )
                    _uiState.value = CharacterDetailState(character = character)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = CharacterDetailState(hasError = true)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun retryLoad(characterId: Int) {
        loadCharacter(characterId)
    }

    fun setErrorState() {
        _uiState.value = _uiState.value.copy(hasError = true, isLoading = false)
    }
}
