package com.dicoding.aetherized.aetherizedgitdeck.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey(Constants.PREFS_KEY_THEME)
    private val homePageKey = stringPreferencesKey(Constants.PREFS_KEY_HOMEPAGE)

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    fun getHomePageSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[homePageKey] ?: "Favorites"
        }
    }

    suspend fun saveHomePageSetting(homepage: String) {
        dataStore.edit { preferences ->
            preferences[homePageKey] = homepage
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}