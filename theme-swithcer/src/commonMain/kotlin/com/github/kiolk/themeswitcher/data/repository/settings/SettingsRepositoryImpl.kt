package com.github.kiolk.themeswitcher.data.repository.settings

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getBooleanOrNullFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val settings: ObservableSettings): SettingsRepository {
    private val _isDarkSystemTheme = MutableStateFlow<Boolean?>(null)
    override val isDarkSystemTheme: Flow<Boolean?> = _isDarkSystemTheme.asStateFlow()

    override fun onSystemThemeChanged(darkTheme: Boolean) {
        _isDarkSystemTheme.value = darkTheme
    }

    @OptIn(ExperimentalSettingsApi::class)
    override fun isUserAppThemeDark(): Flow<Boolean?> {
        val isDark = settings.getBooleanOrNullFlow(IS_DARK_THEM)
        return isDark
    }

    override fun setUserAppThemeDark(isDarkTheme: Boolean) {
        settings.putBoolean(IS_DARK_THEM, isDarkTheme)
    }

    override fun setFollowAsInSystem(followAsInSystem: Boolean) {
        settings.putBoolean(FOLLOW_AS_IN_SYSTEM, followAsInSystem)
    }

    @OptIn(ExperimentalSettingsApi::class)
    override fun getFollowAsInSystem(): Flow<Boolean> {
        return settings.getBooleanOrNullFlow(FOLLOW_AS_IN_SYSTEM).map { it ?: false }
    }

    companion object {
        private const val IS_DARK_THEM = "is_dark_theme"
        private const val FOLLOW_AS_IN_SYSTEM = "follow_as_in_system"
    }
}