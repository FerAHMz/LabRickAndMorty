package com.example.rickandmortylab.main

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortylab.data.AppDatabase
import com.example.rickandmortylab.data.CharacterDb
import com.example.rickandmortylab.data.CharacterEntity
import com.example.rickandmortylab.data.LocationDb
import com.example.rickandmortylab.data.LocationEntity
import com.example.rickandmortylab.datastore.LoginPreferencesKeys
import com.example.rickandmortylab.datastore.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var isSyncing = mutableStateOf(false)
        private set

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val characterDao by lazy { AppDatabase.getDatabase(application).characterDao() }
    private val locationDao by lazy { AppDatabase.getDatabase(application).locationDao() }

    private val characterDb by lazy { CharacterDb() }
    private val locationDb by lazy { LocationDb() }

    init {
        viewModelScope.launch {
            getApplication<Application>().dataStore.data.collect { preferences ->
                val username = preferences[LoginPreferencesKeys.USERNAME_KEY]
                _isLoggedIn.value = username != null
            }
        }
    }

    fun syncData() {
        viewModelScope.launch {
            isSyncing.value = true
            try {
                delay(2000)
                val characters = characterDb.getAllCharacters()
                val locations = locationDb.getAllLocations()

                characterDao.insertCharacters(characters.map {
                    CharacterEntity(
                        id = it.id,
                        name = it.name,
                        status = it.status,
                        species = it.species,
                        gender = it.gender,
                        image = it.image
                    )
                })

                locationDao.insertLocations(locations.map {
                    LocationEntity(
                        id = it.id,
                        name = it.name,
                        type = it.type,
                        dimension = it.dimension
                    )
                })
            } catch (e: Exception) {
            } finally {
                isSyncing.value = false
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences.remove(LoginPreferencesKeys.USERNAME_KEY)
            }
            _isLoggedIn.value = false
        }
    }
}
