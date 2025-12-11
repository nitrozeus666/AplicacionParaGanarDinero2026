package com.antonio.aplicacinparaganardinero2026.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonio.aplicacinparaganardinero2026.R
import com.antonio.aplicacinparaganardinero2026.model.Task
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModel
import java.util.Locale

@Composable
fun AplicacionParaGanarDinero2026Theme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val DarkColors = darkColorScheme(
        primary = Color(0xFF00E5FF),
        secondary = Color(0xFF76FF03),
        background = Color(0xFF121212),
        surface = Color(0xFF1E1E1E),
        onSurface = Color.White
    )

    val LightColors = lightColorScheme(
        primary = Color(0xFF0091EA),
        secondary = Color(0xFF2E7D32),
        background = Color(0xFFF5F5F5),
        surface = Color(0xFFFFFFFF),
        onSurface = Color.Black
    )

    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

// --- 2. PANTALLA PRINCIPAL ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: EarningsViewModel,
    onLanguageChange: (String) -> Unit,
    onTaskClick: (Int) -> Unit,
    onWithdrawClick: () -> Unit
) {
    // Escuchamos los datos vivos del ViewModel
    val balance by viewModel.balance.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    AplicacionParaGanarDinero2026Theme(darkTheme = isDarkMode) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    actions = {
                        // Botón de Modo Oscuro/Claro
                        IconButton(onClick = { viewModel.toogleTheme() }) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = "Toggle Theme",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Botones de Idioma
                        TextButton(onClick = { onLanguageChange("es") }) {
                            Text(
                                "ES",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        TextButton(onClick = { onLanguageChange("en") }) {
                            Text(
                                "EN",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background) // Usa el color de fondo del tema
            ) {
                // Tarjeta de Saldo
                BalanceCard(
                    balance = balance,
                    onWithdraw = onWithdrawClick
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Título de la lista
                Text(
                    text = stringResource(id = R.string.available_tasks),
                    color = MaterialTheme.colorScheme.onSurface, // Se adapta a negro o blanco
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Lista de Tareas
                TaskList(tasks = viewModel.tasks) { taskId ->
                    onTaskClick(taskId)
                }
            }
        }
    }

}

// --- 3. COMPONENTES REUTILIZABLES ---

@Composable
fun BalanceCard(balance: Double, onWithdraw: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    // Mantenemos el gradiente azul siempre, se ve bien en ambos modos
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2962FF), Color(0xFF00E5FF))
                    )
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.current_balance),
                    color = Color.White.copy(alpha = 0.8f) // Siempre blanco porque el fondo es azul
                )
                Text(
                    text = "$${String.format(Locale.US, "%.2f", balance)}",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onWithdraw,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.withdraw_funds),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(tasks: List<Task>, onTaskClick: (Int) -> Unit) { // (Int) -> Unit
    LazyColumn() {
        items(tasks) { task ->
            TaskItem(task, onTaskClick)
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: (Int) -> Unit) { // (Int) -> Unit {
    Card(
        // El color de fondo de la tarjeta cambia según el tema (Blanco o Gris oscuro)
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(task.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = task.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary, // Verde (Neon o Bosque)
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    // Título y descripción usan los IDs de string resources
                    Text(
                        text = stringResource(id = task.title),
                        color = MaterialTheme.colorScheme.onSurface, // Texto adaptativo
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = task.description),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }
            Text(
                text = "+$${task.reward}",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}