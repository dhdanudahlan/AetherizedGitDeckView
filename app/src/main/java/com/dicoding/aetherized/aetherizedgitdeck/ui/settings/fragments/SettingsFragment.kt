package com.dicoding.aetherized.aetherizedgitdeck.ui.settings.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.dicoding.aetherized.aetherizedgitdeck.helper.PrefsViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore by preferencesDataStore(name = "settings")
class SettingsFragment : Fragment() {
    private lateinit var switchTheme: SwitchMaterial
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        switchTheme = view.findViewById(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val settingsViewModel =
            ViewModelProvider(this, PrefsViewModelFactory(pref))[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings()
            .observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }



                switchTheme.setOnCheckedChangeListener { _, isChecked ->
                    settingsViewModel.saveThemeSetting(isChecked)
                }
            }
        return view
    }
}