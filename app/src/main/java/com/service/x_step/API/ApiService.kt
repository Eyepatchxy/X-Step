package com.service.x_step.API

import retrofit2.Call
import com.service.x_step.data_classes.NotificationData
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/send-notification")
    fun sendNotification(@Body data: NotificationData): Call<ResponseBody>
}