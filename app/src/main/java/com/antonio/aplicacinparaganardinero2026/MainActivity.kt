package com.antonio.aplicacinparaganardinero2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.antonio.aplicacinparaganardinero2026.ui.theme.AplicaciónParaGanarDinero2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicaciónParaGanarDinero2026Theme {}
        }
    }
}