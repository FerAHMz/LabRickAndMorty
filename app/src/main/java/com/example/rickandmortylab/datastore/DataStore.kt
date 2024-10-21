package com.example.rickandmortylab.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_preferences")

object LoginPreferencesKeys {
    val USERNAME_KEY = stringPreferencesKey("username")
}

class UserPreferences(private val context: Context) {

    val username: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LoginPreferencesKeys.USERNAME_KEY]
        }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[LoginPreferencesKeys.USERNAME_KEY] = username
        }
    }

    suspend fun clearUsername() {
        context.dataStore.edit { preferences ->
            preferences.remove(LoginPreferencesKeys.USERNAME_KEY)
        }
    }
}
