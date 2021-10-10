package com.example.githubuser.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfiq {

    companion object {

        private const val BASE_URL = "https://api.github.com/"

        fun getApiSearches(): ApiSearch {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
            return retrofit.create(ApiSearch::class.java)
        }

        fun getApiFollowers(): ApiFollowers {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
            return retrofit.create(ApiFollowers::class.java)
        }

        fun getApiFollowings(): ApiFollowings {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
            return retrofit.create(ApiFollowings::class.java)
        }
    }
}