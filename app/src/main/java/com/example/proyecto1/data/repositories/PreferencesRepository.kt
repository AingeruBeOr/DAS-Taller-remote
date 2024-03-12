package com.example.proyecto1.data.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject


/**
 * Oficial documentation: https://developer.android.com/topic/libraries/architecture/datastore
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository @Inject constructor(
    private val context: Context
) {
    // Defining keys from DataStore
    private val THEME = stringPreferencesKey("theme")

    fun getUserTheme(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME] ?: "Blue"
        }
    }

    suspend fun saveUserTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = theme
        }
    }
}