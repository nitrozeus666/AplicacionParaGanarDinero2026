package com.antonio.aplicacinparaganardinero2026

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antonio.aplicacinparaganardinero2026.model.UserPreferences
import com.antonio.aplicacinparaganardinero2026.screen.HomeScreen
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModel
import com.antonio.aplicacinparaganardinero2026.viewmodel.EarningsViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = UserPreferences(applicationContext)

        setContent {
            val viewModel: EarningsViewModel = viewModel(
                factory = EarningsViewModelFactory(userPreferences)
            )

            HomeScreen(
                viewModel = viewModel,
                onLanguageChange = { code -> changeLanguage(code)}
            )
        }
    }

    private fun changeLanguage(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}