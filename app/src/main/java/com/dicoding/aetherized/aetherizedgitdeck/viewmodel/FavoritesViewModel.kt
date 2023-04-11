package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.local.repository.FavoriteRepository
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig

class FavoritesViewModel (application: Application) : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> get() = _login

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val apiService = ApiConfig.getApiService()

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    companion object {
        private const val TAG = "FavoritesViewModel"
    }

    init {
        _isLoading.postValue(true)
    }
    fun getAllFavorites(): LiveData<List<User>>{
        _isLoading.postValue(false)
        return mFavoriteRepository.getAllFavorites()
    }
}