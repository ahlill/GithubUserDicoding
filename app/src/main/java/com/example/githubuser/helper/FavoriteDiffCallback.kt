package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.database.FavoriteDataModel

class FavoriteDiffCallback(private val mOldFavoriteDataModelLIst: List<FavoriteDataModel>, private val mNewFavoriteDataModelList: List<FavoriteDataModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteDataModelLIst.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteDataModelList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteDataModelLIst[oldItemPosition].id == mNewFavoriteDataModelList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = mOldFavoriteDataModelLIst[oldItemPosition]
        val newList = mNewFavoriteDataModelList[newItemPosition]

        return oldList.login == newList.login && oldList.id == newList.id
    }
}