package com.example.myapplication.api

import com.example.myapplication.models.UsageMetrics
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WellnessWaveApi {
    @POST("/daily-data")
    fun submitDailyData(@Body metrics: UsageMetrics): Call<Map<String, Any>>

    @GET("/prediction")
    fun getPrediction(@Query("user_id") userId: String): Call<com.example.myapplication.models.PredictionResponse>
}
