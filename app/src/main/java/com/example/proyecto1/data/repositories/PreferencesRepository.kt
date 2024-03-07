package com.example.proyecto1.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject


/**
 * Oficial documentation: https://developer.android.com/topic/libraries/architecture/datastore
 */

val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesRepository @Inject constructor(
    val context: Context
) {
    // Defining keys from DataStore
    val LANGUAGE = stringPreferencesKey("lang")


    // --- LANGUAGE PREFERENCES ---
    suspend fun getCurrentLanguage() = context.dataStore.data.first()[LANGUAGE]

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
}