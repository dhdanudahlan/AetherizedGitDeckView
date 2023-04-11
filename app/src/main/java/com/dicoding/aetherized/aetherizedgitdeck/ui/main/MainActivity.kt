package com.dicoding.aetherized.aetherizedgitdeck.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.dicoding.aetherized.aetherizedgitdeck.helper.PrefsViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments.BrowseFragment
import com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments.FavoritesFragment
import com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments.MoreFragment
import com.dicoding.aetherized.aetherizedgitdeck.ui.settings.SettingsActivity
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.SettingsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var toolBar: Toolbar
    private lateinit var toolbarTitle1: String
    private lateinit var toolbarTitle2: String
    private lateinit var toolbarTitle3: String
    lateinit var toolbarTitleDetails: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeValue()
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, PrefsViewModelFactory(pref))[SettingsViewModel::class.java]


        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        settingsViewModel.getHomePageSettings().observe(this) { homepage ->
            bottomNav(homepage)
        }

    }
    private fun initializeValue(){
        toolbarTitle1 = resources.getString(R.string.toolBarTitle1)
        toolbarTitle2 = resources.getString(R.string.toolBarTitle2)
        toolbarTitle3 = resources.getString(R.string.toolBarTitle3)
        toolbarTitleDetails = "User Details"
    }

    private fun bottomNav(homepage: String) {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        when (homepage) {
            "Favorites" -> {
                val fragment = FavoritesFragment()
                replaceFragment(fragment)
                replaceToobar(toolbarTitle1)
                bottomNavigation.selectedItemId = R.id.favorites
            }
            "Browse" -> {
                val fragment = BrowseFragment()
                replaceFragment(fragment)
                replaceToobar(toolbarTitle2)
                bottomNavigation.selectedItemId = R.id.browse
            }
            "More" -> {
                val fragment = MoreFragment()
                replaceFragment(fragment)
                replaceToobar(toolbarTitle3)
                bottomNavigation.selectedItemId = R.id.more
            }
            else -> {
                val fragment = FavoritesFragment()
                replaceFragment(fragment)
                replaceToobar(toolbarTitle1)
                bottomNavigation.selectedItemId = R.id.favorites
            }
        }

        bottomNavigation.setOnItemSelectedListener  { item ->
            val fragment = when(item.itemId) {
                R.id.favorites -> FavoritesFragment()
                R.id.browse -> BrowseFragment()
                R.id.more -> MoreFragment()
                else -> BrowseFragment()
            }
            when(item.itemId) {
                R.id.favorites -> replaceToobar(toolbarTitle1)
                R.id.browse -> replaceToobar(toolbarTitle2)
                R.id.more -> replaceToobar(toolbarTitle3)
                else -> replaceToobar(toolbarTitle1)
            }
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if(currentFragment?.javaClass != fragment.javaClass) {
                replaceFragment(fragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
    fun replaceToobar(title: String) {
        toolBar.title= title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}