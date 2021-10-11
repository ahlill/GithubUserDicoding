package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteDataModel(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_position")
        var idPosition: Int = 0,

        @ColumnInfo(name = "id")
        var id: Int? = 0,

        @ColumnInfo(name = "login")
        var login: String? = null,

        @ColumnInfo(name = "avatar_url")
        var avatarUrl: String? = null,

        @ColumnInfo(name = "html_url")
        var htmlUrl: String? = null
) : Parcelable