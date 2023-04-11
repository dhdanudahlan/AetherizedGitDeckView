package com.dicoding.aetherized.aetherizedgitdeck.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,

    @SerializedName("login")
    @ColumnInfo(name = "username")
    val username: String,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int
) : Parcelable


