package com.antonio.aplicacinparaganardinero2026.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.antonio.aplicacinparaganardinero2026.model.Task


class EarningsViewModel : ViewModel() {
    var balance by mutableStateOf(0.00)
        private set

    val tasks = listOf(
        Task(1, "Entrenar IA de Visión", "Identifica objetos en 5 fotos", 0.50, Icons.Default.CheckCircle),
        Task(2, "Ver anuncio Holográfico", "Videos patrocinado de 30s", 0.15, Icons.Default.AttachMoney),
        Task(3, "Validar Traducción", "¿Es correcto este texto?", 0.30, Icons.Default.CheckCircle),
        Task(4, "Compartir Ancho de Banda", "5 mins de red compartida", 0.10, Icons.Default.Wallet)
    )

    fun completeTask(reward: Double) {
        balance += reward
    }

    fun withdraw() {
        balance = 0.0
    }
}