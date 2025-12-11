package com.antonio.aplicacinparaganardinero2026.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(
    viewModel: EarningsViewModel,
    onNavigateBack: () -> Unit
) {
    val balance by viewModel.balance.collectAsState()

    // Estado del formulario
    var amountText by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("PayPal") }
    var showSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Retirar Fondos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            // Tarjeta de resumen
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Disponible para retirar:", color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "$${String.format("%.2f", balance)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Método de Pago", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            // Botones de método de pago
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PaymentMethodButton("PayPal", selectedMethod) { selectedMethod = "PayPal" }
                PaymentMethodButton("Crypto", selectedMethod) { selectedMethod = "Crypto" }
                PaymentMethodButton("Bank", selectedMethod) { selectedMethod = "Bank" }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de entrada
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Cantidad ($)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de Confirmar
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()
                    if (amount != null) {
                        val success = viewModel.withdrawAmount(amount)
                        if (success) {
                            showSuccess = true
                            errorMessage = ""
                            amountText = ""
                        } else {
                            errorMessage = "Saldo insuficiente"
                        }
                    } else {
                        errorMessage = "Ingresa una cantidad válida"
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("CONFIRMAR RETIRO", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        // Popup de Éxito (Dialog)
        if (showSuccess) {
            AlertDialog(
                onDismissRequest = { showSuccess = false },
                icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
                title = { Text("¡Retiro Procesado!") },
                text = { Text("Has retirado el dinero a tu cuenta de $selectedMethod correctamente.") },
                confirmButton = {
                    TextButton(onClick = {
                        showSuccess = false
                        onNavigateBack() // Volver al inicio al terminar
                    }) {
                        Text("GENIAL")
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun RowScope.PaymentMethodButton(name: String, selected: String, onClick: () -> Unit) {
    val isSelected = name == selected
    Box(
        modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}