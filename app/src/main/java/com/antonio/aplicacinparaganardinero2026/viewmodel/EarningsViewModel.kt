package com.antonio.aplicacinparaganardinero2026.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Wallet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.antonio.aplicacinparaganardinero2026.R
import com.antonio.aplicacinparaganardinero2026.model.Task
import com.antonio.aplicacinparaganardinero2026.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class EarningsViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    private val _balance = MutableStateFlow(0.00)
    val balance: StateFlow<Double> = _balance.asStateFlow()

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferences.balance.collect { _balance.value = it }
        }
        viewModelScope.launch {
            userPreferences.isDarkMode.collect { _isDarkMode.value = it }
        }
    }

    val tasks = listOf(
        Task(1, R.string.task_vision_title, R.string.task_vision_desc, 0.50, Icons.Default.CheckCircle),
        Task(2, R.string.task_ad_title, R.string.task_ad_desc, 0.15, Icons.Default.AttachMoney),
        Task(3, R.string.task_translate_title, R.string.task_translate_desc, 0.30, Icons.Default.CheckCircle),
        Task(4, R.string.task_bandwidth_title, R.string.task_bandwidth_desc, 0.10, Icons.Default.Wallet)
    )

    fun toggleTheme() {
        viewModelScope.launch {
            userPreferences.saveTheme(!_isDarkMode.value)
        }
    }

    fun completeTask(reward: Double) {
        val newTotal = _balance.value + reward
        updateBalance(newTotal)
    }

    fun withdraw() {
        updateBalance(0.0)
    }

    private fun updateBalance(amount: Double) {
        viewModelScope.launch {
            userPreferences.saveBalance(amount)
        }
    }

    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun withdrawAmount(amount: Double): Boolean {
        if (amount > 0 && amount <= _balance.value) {
            val newBalance = _balance.value - amount
            updateBalance(newBalance)
            return true
        }
        return false
    }
}

class EarningsViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EarningsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EarningsViewModel(userPreferences) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}