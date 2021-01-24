package com.example.fcmapp

import com.example.fcmapp.Constants.Companion.CONTENT_TYPE
import com.example.fcmapp.Constants.Companion.SERVICE_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * Ahmed ali
 * 24/01/2021
 */
interface NotificationApi {

    @Headers("Authorization: key=$SERVICE_KEY","Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ):Response<ResponseBody>

}