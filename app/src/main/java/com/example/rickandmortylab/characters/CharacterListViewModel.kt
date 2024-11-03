package com.example.rickandmortylab.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.CharacterDao
import com.example.rickandmortylab.data.CharacterEntity
import com.example.rickandmortylab.model.Character
import com.example.rickandmortylab.RickAndMortyApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterListViewModel(
    private val apiClient: RickAndMortyApiClient,
    private val characterDao: CharacterDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterListState())
    val uiState: StateFlow<CharacterListState> = _uiState

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val characters = apiClient.getCharacters()
                val characterEntities = characters.map {
                    CharacterEntity(it.id, it.name, it.status, it.species, it.gender, it.image)
                }

                withContext(Dispatchers.IO) {
                    characterDao.insertCharacters(characterEntities)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = characterEntities.map { entity ->
                            Character(entity.id, entity.name, entity.status, entity.species, entity.gender, entity.image)
                        }
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()

                val localCharacters = withContext(Dispatchers.IO) {
                    characterDao.getAllCharacters()
                }
                if (localCharacters.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = localCharacters.map { entity ->
                                Character(entity.id, entity.name, entity.status, entity.species, entity.gender, entity.image)
                            }
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, hasError = true) }
                }
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
