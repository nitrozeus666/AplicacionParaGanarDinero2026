package com.antonio.aplicacinparaganardinero2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.antonio.aplicacinparaganardinero2026.screen.HomeScreen
import com.antonio.aplicacinparaganardinero2026.ui.theme.AplicaciónParaGanarDinero2026Theme

class MainActivity : ComponentActivity() {
    class MainActivity: AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                AplicaciónParaGanarDinero2026Theme {
                    HomeScreen(
                        onLanguageChange = { languageCode ->
                            changeLanguage(languageCode)
                        }
                    )
                }
            }
        }

        private fun changeLanguage(languageCode: String) {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }
}