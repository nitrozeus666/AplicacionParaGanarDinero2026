package com.antonio.aplicacinparaganardinero2026.model

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val BALANCE_KEY = doublePreferencesKey("user_balance")
    }

    val balance: Flow<Double> = context.dataStore.data
        .map { preferences ->
            preferences[BALANCE_KEY] ?: 0.00
        }

    suspend fun saveBalance(amount: Double) {
        context.dataStore.edit { preferences ->
            preferences[BALANCE_KEY] = amount
        }
    }
}