package com.dicoding.aetherized.aetherizedgitdeck.data

import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("items")
    val users: List<User>
)
