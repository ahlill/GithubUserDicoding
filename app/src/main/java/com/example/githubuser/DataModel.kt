package com.example.githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DataModel(
	@field:SerializedName("users") val users: List<UsersItem?>? = null
)

@Parcelize
data class UsersItem(
	@field:SerializedName("follower")	val follower: Int? = null,
	@field:SerializedName("following") val following: Int? = null,
	@field:SerializedName("name")	val name: String? = null,
	@field:SerializedName("company") val company: String? = null,
	@field:SerializedName("location")	val location: String? = null,
	@field:SerializedName("avatar") val avatar: String? = null,
	@field:SerializedName("repository") val repository: Int? = null,
	@field:SerializedName("username")	val username: String? = null
) : Parcelable
