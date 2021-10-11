package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteDataModel: FavoriteDataModel)

    @Delete
    fun delete(favoriteDataModel: FavoriteDataModel)

    @Query("SELECT * from favoritedatamodel ORDER BY id_position ASC")
    fun getAllFavorites(): LiveData<List<FavoriteDataModel>>
}