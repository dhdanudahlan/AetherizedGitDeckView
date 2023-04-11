package com.dicoding.aetherized.aetherizedgitdeck.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.databinding.ActivitySettingsBinding
import com.dicoding.aetherized.aetherizedgitdeck.ui.settings.fragments.PrefsFragment


//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var toolBar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
//                .replace(R.id.fragment_container, SettingsFragment())
                .replace(R.id.fragment_container, PrefsFragment())
                .commit()
        }
    }
}



