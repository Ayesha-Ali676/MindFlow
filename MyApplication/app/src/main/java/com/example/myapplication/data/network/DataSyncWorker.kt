package com.example.myapplication.data.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.data.UsageDataManager

class DataSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val usageDataManager = UsageDataManager(applicationContext)
        val api = RetrofitClient.instance

        return try {
            // Fetch default mood score (0 indicates no manual log yet)
            val metrics = usageDataManager.getDailyMetrics(moodScore = 0)
            
            Log.d("DataSyncWorker", "Attempting to sync behavioral data for user: ${metrics.user_id}")
            
            val response = api.submitDailyData(metrics)
            
            if (response.isSuccessful) {
                Log.d("DataSyncWorker", "Successfully synced data to backend.")
                Result.success()
            } else {
                Log.e("DataSyncWorker", "Failed to sync data: ${response.errorBody()?.string()}")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e("DataSyncWorker", "Error during data sync", e)
            Result.retry()
        }
    }
}
