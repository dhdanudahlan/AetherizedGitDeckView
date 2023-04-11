package com.dicoding.aetherized.aetherizedgitdeck.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.local.dao.FavoriteDao
import com.dicoding.aetherized.aetherizedgitdeck.data.local.database.FavoriteRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<User>> = mFavoriteDao.getAllFavorites()

    suspend fun getFavoriteByUsername(username: String): User? = withContext(Dispatchers.IO) {
        return@withContext mFavoriteDao.getFavoriteByUsername(username)
    }

    fun insert(user: User) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun delete(user: User) {
        executorService.execute { mFavoriteDao.delete(user) }
    }

}