package com.example.proyecto1.data.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject


/**
 * Oficial documentation: https://developer.android.com/topic/libraries/architecture/datastore
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository @Inject constructor(
    val context: Context
) {
    // Defining keys from DataStore
    val LANGUAGE = stringPreferencesKey("lang")

    val actualLanguage: String
        get() = runBlocking {
            Log.d("Language", "PreferencesRepository getSavedLanguage(): ${context.dataStore.data.first()[LANGUAGE]}" ?: "no hay")
            return@runBlocking context.dataStore.data.first()[LANGUAGE] ?: Locale.getDefault().language
        }


    // --- LANGUAGE PREFERENCES ---
    // Get saved language. If not exits, returns the device default
    /*suspend fun getSavedLanguage(): String {
    }*/

    suspend fun saveLanguage(language: String) {
        Log.d("Language", "PreferencesRepository saveLanguage(): Setting language to $language")
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
}