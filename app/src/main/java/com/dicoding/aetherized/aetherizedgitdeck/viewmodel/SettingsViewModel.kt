package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.launch

//private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsViewModel (private val pref: SettingPreferences) : ViewModel() {
    private val _prefDarkMode = MutableLiveData<Boolean>()
    val prefDarkMode: LiveData<Boolean> get() = _prefDarkMode


    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }

    fun getHomePageSettings(): LiveData<String> {
        return pref.getHomePageSetting().asLiveData()
    }

    fun saveHomePageSetting(homepage: String) {
        viewModelScope.launch {
            pref.saveHomePageSetting(homepage)
        }
    }
}