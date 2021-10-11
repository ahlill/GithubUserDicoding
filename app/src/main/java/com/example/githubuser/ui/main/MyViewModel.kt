package com.example.githubuser.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.api.DataModel
import com.example.githubuser.api.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {

    private val _dataSearches = MutableLiveData<DataModel>()
    val dataSearches: LiveData<DataModel> = _dataSearches

    private val _dataFollowings = MutableLiveData<List<ItemsItem>>()
    val dataFollowings: LiveData<List<ItemsItem>> = _dataFollowings

    private val _dataFollowers = MutableLiveData<List<ItemsItem>>()
    val dataFollowers: LiveData<List<ItemsItem>> = _dataFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    private val _isLoadingFollowings = MutableLiveData<Boolean>()
    val isLoadingFollowings: LiveData<Boolean> = _isLoadingFollowings

    fun findDataSearches(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiSearches().getSearches(username)
        client.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                    call: Call<DataModel>,
                    response: Response<DataModel>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _dataSearches.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: $RES_ERR")
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: $FAILURE")
            }

        })
    }

    fun findDataFollowings(username: String?) {
        _isLoadingFollowings.value = true
        val client = ApiConfig.getApiFollowings().getFollowings(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowings.value = false
                if (response.isSuccessful) {
                    _dataFollowings.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: $RES_ERR")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowings.value = false
            }

        })
    }

    fun findDataFollowers(username: String?) {
        _isLoadingFollowers.value = true
        val client = ApiConfig.getApiFollowers().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowers.value = false
                if (response.isSuccessful) {
                    _dataFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: $RES_ERR")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowers.value = false
                Log.e(TAG, "onFailure: $FAILURE")
            }

        })
    }

    companion object {
        private const val RES_ERR = "Respond Error"
        private const val FAILURE = "Gagal Menyambungkan"
    }
}