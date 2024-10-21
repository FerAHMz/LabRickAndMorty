package com.example.rickandmortylab.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.rickandmortylab.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    fun saveUsername(username: String, context: Context) {
        val dataStoreKey = stringPreferencesKey("username")
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = username
            }
        }
    }

    fun getUsername(context: Context): Flow<String?> {
        val dataStoreKey = stringPreferencesKey("username")
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey]
        }
    }

    fun clearUsername(context: Context) {
        val dataStoreKey = stringPreferencesKey("username")
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences.remove(dataStoreKey)
            }
        }
    }
}

