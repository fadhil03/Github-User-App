package com.fdl.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: SettingsPreferenceAppTheme) : ViewModel() {

    fun setThemeMode(isNightMode: Boolean) {
        viewModelScope.launch { preferences.setThemeMode(isNightMode) }
    }

    fun getThemeMode(): LiveData<Boolean> {
        return preferences.getThemeMode().asLiveData()
    }
}