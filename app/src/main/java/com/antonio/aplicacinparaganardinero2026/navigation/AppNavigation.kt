package com.antonio.aplicacinparaganardinero2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antonio.aplicacinparaganardinero2026.screen.AplicacionParaGanarDinero2026Theme
import com.antonio.aplicacinparaganardinero2026.screen.HomeScreen
import com.antonio.aplicacinparaganardinero2026.screen.ProfileScreen
import com.antonio.aplicacinparaganardinero2026.screen.TaskDetailScreen
import com.antonio.aplicacinparaganardinero2026.screen.WithdrawScreen
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModel

@Composable
fun AppNavigation(
    viewModel: EarningsViewModel,
    onLanguageChange: (String) -> Unit
) {
    val navController = rememberNavController()

    // 1. Leemos el estado del modo oscuro desde el ViewModel
    //    para aplicarlo a TODA la aplicación desde aquí.
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    // 2. Envolvemos la navegación en el Tema para que los colores funcionen
    AplicacionParaGanarDinero2026Theme(darkTheme = isDarkMode) {

        NavHost(navController = navController, startDestination = "home") {

            // --- PANTALLA 1: HOME ---
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onLanguageChange = onLanguageChange,
                    // Cuando tocan una tarea, navegamos a detalles pasando el ID
                    onTaskClick = { taskId ->
                        navController.navigate("detail/$taskId")
                    },
                    // Cuando tocan "Retirar Fondos", navegamos a la pantalla de retiro
                    onWithdrawClick = {
                        navController.navigate("withdraw")
                    }
                )
            }

            // --- PANTALLA 2: DETALLES DE TAREA ---
            composable(
                route = "detail/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Recuperamos el ID que pasamos en la ruta
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

                TaskDetailScreen(
                    taskId = taskId,
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack() // Volver atrás
                    }
                )
            }

            // --- PANTALLA 3: RETIRAR FONDOS ---
            composable("withdraw") {
                WithdrawScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack() // Volver atrás
                    }
                )
            }

            composable("profile") {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack()}
                )
            }
        }
    }
}