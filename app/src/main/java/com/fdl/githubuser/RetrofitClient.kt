package com.fdl.githubuser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL_API = "https://api.github.com/"

    val instance: ApiServices by lazy {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitClient.create(ApiServices::class.java)
    }
}