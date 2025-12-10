package com.antonio.aplicacinparaganardinero2026.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val reward: Double,
    val icon: ImageVector
)
