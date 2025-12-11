package com.antonio.aplicacinparaganardinero2026.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Task(
    val id: Int,
    @StringRes val title: Int,                                //cambio: String -> Int
    @StringRes val description: Int,                         //cambio: String -> Int
    val reward: Double,
    val icon: ImageVector
)
