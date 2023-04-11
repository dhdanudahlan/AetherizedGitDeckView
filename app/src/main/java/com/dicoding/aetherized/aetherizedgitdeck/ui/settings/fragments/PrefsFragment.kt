package com.dicoding.aetherized.aetherizedgitdeck.ui.settings.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.dicoding.aetherized.aetherizedgitdeck.helper.PrefsViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.SettingsViewModel

private val Context.dataStore by preferencesDataStore(name = "settings")
class PrefsFragment : PreferenceFragmentCompat() {

    private lateinit var switchTheme: SwitchPreferenceCompat
    private lateinit var homePage: ListPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        switchTheme = findPreference("light_switch")!!
        homePage = findPreference("home_page")!!


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingsViewModel = ViewModelProvider(this, PrefsViewModelFactory(pref))[SettingsViewModel::class.java]


        settingsViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnPreferenceChangeListener { _, newValue ->
            val isChecked = newValue as Boolean
            settingsViewModel.saveThemeSetting(isChecked)
            true
        }

        homePage.setOnPreferenceChangeListener { _, newValue ->
            val homepage = newValue.toString()
            settingsViewModel.saveHomePageSetting(homepage)
            showToast(homepage)
            true
        }
        return view
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}