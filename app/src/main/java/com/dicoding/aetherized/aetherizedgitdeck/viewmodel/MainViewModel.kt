package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.dicoding.aetherized.aetherizedgitdeck.data.SettingPreferences
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.local.repository.FavoriteRepository
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig
import kotlinx.coroutines.launch

class MainViewModel( private val application: Application) : ViewModel() {
    private val _searchKeywords = MutableLiveData<String>()
    val searchKeywords : LiveData<String> get() = _searchKeywords

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    private val apiService = ApiConfig.getApiService()

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

}