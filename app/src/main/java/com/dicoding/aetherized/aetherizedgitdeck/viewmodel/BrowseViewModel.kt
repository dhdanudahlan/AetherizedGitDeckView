package com.dicoding.aetherized.aetherizedgitdeck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.UsersResponse
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowseViewModel : ViewModel() {
    private val _searchKeywords = MutableLiveData<String>()
    val searchKeywords : LiveData<String> get() = _searchKeywords

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    private val apiService = ApiConfig.getApiService()
    init {
        getUsers()
    }
    private fun getUsers() {
        _isLoading.postValue(true)
        apiService.findUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val users = response.body()?: emptyList()
                    _users.postValue(users)
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue("An error occurred while fetching data. Please check your internet connection and try again.")
            }
        })
    }
    fun searchUsers(query: String) {
        if (query.isBlank()) return

        _isLoading.postValue(true)
        _searchKeywords.postValue(query)

        apiService.searchUsers(query, 30).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val users = response.body()?.users ?: emptyList()
                    _users.postValue(users)
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue("An error occurred while fetching data. Please check your internet connection and try again.")
            }
        })
    }
    fun setKeywords(keywords: String){
        this._searchKeywords.postValue(keywords)
    }
}


