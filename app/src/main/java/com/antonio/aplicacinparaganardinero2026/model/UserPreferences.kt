package com.antonio.aplicacinparaganardinero2026.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val BALANCE_KEY = doublePreferencesKey("user_balance")
        val THEME_KEY = booleanPreferencesKey("is_dark_mode")
    }

    val balance: Flow<Double> = context.dataStore.data
        .map { preferences ->
            preferences[BALANCE_KEY] ?: 0.00
        }

    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[THEME_KEY] ?: true }

    suspend fun saveBalance(amount: Double) {
        context.dataStore.edit { preferences ->
            preferences[BALANCE_KEY] = amount
        }
    }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences -> preferences[THEME_KEY] = isDark }
    }
}