package com.github.kiolk.themeswitcher.data.repository.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val isDarkSystemTheme: Flow<Boolean?>

    fun onSystemThemeChanged(darkTheme: Boolean)

    fun isUserAppThemeDark(): Flow<Boolean?>

    fun setUserAppThemeDark(isDarkTheme: Boolean)

    fun setFollowAsInSystem(followAsInSystem: Boolean)

    fun getFollowAsInSystem(): Flow<Boolean>
}