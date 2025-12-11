package com.antonio.aplicacinparaganardinero2026.model

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val date: String,
    val isDeposit: Boolean
)
