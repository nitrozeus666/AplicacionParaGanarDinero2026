package com.antonio.aplicacinparaganardinero2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.antonio.aplicacinparaganardinero2026.screen.AplicaciónParaGanarDinero2026Theme
import com.antonio.aplicacinparaganardinero2026.screen.HomeScreen
import com.antonio.aplicacinparaganardinero2026.screen.TaskDetailScreen
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModel

@Composable
fun AppNavigation(
    viewModel: EarningsViewModel,
    onLanguageChange: (String) -> Unit
) {
    val navController = rememberNavController()

    val isDarkMode by viewModel.isDarkMode.collectAsState()

    AplicaciónParaGanarDinero2026Theme(darkTheme = isDarkMode) {

        NavHost(navController = navController, startDestination = "home") {

            // RUTA 1: HOME
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onLanguageChange = onLanguageChange,
                    onTaskClick = { taskId ->
                        navController.navigate("detail/$taskId")
                    }
                )
            }

            // RUTA 2: DETALLES
            composable(
                route = "detail/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

                TaskDetailScreen(
                    taskId = taskId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}