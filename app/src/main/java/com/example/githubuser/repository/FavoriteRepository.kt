package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteDao
import com.example.githubuser.database.FavoriteDataModel
import com.example.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteDataModel>> = mFavoriteDao.getAllFavorites()

    fun insert(favoriteDataModel: FavoriteDataModel) {
        executorService.execute { mFavoriteDao.insert(favoriteDataModel) }
    }

    fun delete(favoriteDataModel: FavoriteDataModel) {
        executorService.execute { mFavoriteDao.delete(favoriteDataModel) }
    }


}