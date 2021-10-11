package com.example.githubuser.ui.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
        var id: Int? = 0,
        var login: String? = null,
        var avatarUrl: String? = null,
        var htmlUrl: String? = null
) : Parcelable
