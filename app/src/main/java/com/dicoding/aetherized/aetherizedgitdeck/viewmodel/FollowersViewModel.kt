package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String> get() = _login

    private val _followers = MutableLiveData<List<User>>()
    val followers: LiveData<List<User>> get() = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val apiService = ApiConfig.getApiService()

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    fun initializer(username: String) {
        setLogin(username)
        findFollowers(username)
    }
    private fun setLogin(Login: String) {
        this._login.postValue(Login)
    }
    private fun findFollowers(username: String) {
        _isLoading.value = true
        val client = apiService.getUserFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.postValue(responseBody!!)
                    }
                } else {
                    Log.e(TAG, "onFailure findFollowers: $username")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}