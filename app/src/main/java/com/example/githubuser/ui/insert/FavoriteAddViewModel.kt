package com.example.githubuser.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteDataModel
import com.example.githubuser.repository.FavoriteRepository

class FavoriteAddViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: FavoriteDataModel) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: FavoriteDataModel) {
        mFavoriteRepository.delete(favorite)
    }

    fun getAllFavorite(): LiveData<List<FavoriteDataModel>> = mFavoriteRepository.getAllFavorites()

}