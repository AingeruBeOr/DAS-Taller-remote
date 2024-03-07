package com.example.proyecto1.data.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.util.Locale
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
    // Get saved language. If not exits, returns the device default
    suspend fun getSavedLanguage(): String {
        Log.d("PreferencesRepository", context.dataStore.data.first()[LANGUAGE] ?: "no hay")
        return context.dataStore.data.first()[LANGUAGE] ?: Locale.getDefault().language
    }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
}