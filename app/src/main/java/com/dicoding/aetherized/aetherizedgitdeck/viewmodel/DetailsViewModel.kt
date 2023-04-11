package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.local.repository.FavoriteRepository
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application) : ViewModel() {
    private val _login = MutableLiveData<String>()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isInFavorites = MutableLiveData<Boolean>()
    val isInFavorites: LiveData<Boolean> get() = _isInFavorites

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val apiService = ApiConfig.getApiService()

    companion object {
        private const val TAG = "DetailsViewModel"
    }

    fun initializer(username: String) {
        setLogin(username)
    }
    private fun setLogin(Login: String) {
        this._login.postValue(Login)
        findUser(Login)
    }

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(user: User){
        mFavoriteRepository.insert(user)
    }

    fun delete(user: User){
        mFavoriteRepository.delete(user)
    }


    private fun findUser(username: String) {
        _isLoading.postValue(true)
        val client = apiService.getUserDetails(username)
        client.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.postValue(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure findUser: $username")

//                    inFavoritesMsg("response un Successful")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
//                inFavoritesMsg("on Failure")
            }
        })
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorite = mFavoriteRepository.getFavoriteByUsername(username)
                val fav = favorite?.username ?: "NONE"
                _isInFavorites.postValue(favorite != null)
            }
        }
    }

    fun toggleFavorites() {
        val user = user.value ?: return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorite = mFavoriteRepository.getFavoriteByUsername(user.username)
                if (favorite == null) {
                    mFavoriteRepository.insert(
                        User(
                            id = user.id,
                            username = user.username,
                            avatarUrl = user.avatarUrl,
                            name = user.name,
                            followers = user.followers,
                            following = user.following
                        )
                    )
                    _isInFavorites.postValue(true)
                } else {
                    mFavoriteRepository.delete(favorite)
                    _isInFavorites.postValue(false)
                }
            }
        }
    }
}