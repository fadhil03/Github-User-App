package com.fdl.githubuser

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferenceAppTheme private constructor(private val dataStore: DataStore<Preferences>) {

    private val keyThemeApp = booleanPreferencesKey("theme_mode")

    companion object {
        @Volatile
        private var instance: SettingsPreferenceAppTheme? = null

        fun newInstance(dataStore: DataStore<Preferences>): SettingsPreferenceAppTheme {
            return instance ?: synchronized(this) {
                val ins = SettingsPreferenceAppTheme(dataStore)
                instance = ins
                ins
            }
        }
    }

    fun getThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[keyThemeApp] ?: false }
    }

    suspend fun setThemeMode(isNightMode: Boolean) {
        dataStore.edit { preferences -> preferences [keyThemeApp] = isNightMode }
    }
}