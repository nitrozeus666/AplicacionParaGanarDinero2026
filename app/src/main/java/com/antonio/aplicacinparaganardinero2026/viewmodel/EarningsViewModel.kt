package com.antonio.aplicacinparaganardinero2026.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.antonio.aplicacinparaganardinero2026.R
import com.antonio.aplicacinparaganardinero2026.model.Task


class EarningsViewModel : ViewModel() {
    var balance by mutableStateOf(0.00)
        private set

    val tasks = listOf(
        Task(1, R.string.task_vision_title, R.string.task_vision_desc, 0.50, Icons.Default.CheckCircle),
        Task(2, R.string.task_ad_title, R.string.task_ad_desc, 0.15, Icons.Default.AttachMoney),
        Task(3, R.string.task_translate_title, R.string.task_translate_desc, 0.30, Icons.Default.CheckCircle),
        Task(4, R.string.task_bandwidth_title, R.string.task_bandwidth_desc, 0.10, Icons.Default.Wallet)
    )

    fun completeTask(reward: Double) {
        balance += reward
    }

    fun withdraw() {
        balance = 0.0
    }
}