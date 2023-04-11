package com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.dicoding.aetherized.aetherizedgitdeck.helper.PrefsViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.ui.settings.SettingsActivity
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.MoreViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MoreFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        return view
    }
}
