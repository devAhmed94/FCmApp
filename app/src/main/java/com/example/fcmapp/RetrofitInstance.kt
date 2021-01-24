package com.example.fcmapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Ahmed ali
 * 24/01/2021
 */
object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
    val Api by lazy {
        retrofit.create(NotificationApi::class.java)
    }
}