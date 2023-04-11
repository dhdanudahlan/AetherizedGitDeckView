package com.dicoding.aetherized.aetherizedgitdeck.data.local.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.aetherized.aetherizedgitdeck.data.User

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getFavoriteByUsername(username: String): User?
}