package com.example.myapplication.data

import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.*
import java.util.concurrent.TimeUnit

class UsageDataManager(private val context: Context) {

    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    fun getDailyMetrics(moodScore: Int): com.example.myapplication.models.UsageMetrics {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis

        val stats = usageStatsManager.queryAndAggregateUsageStats(startTime, endTime)
        
        var totalScreenTimeMs = 0L
        var socialTimeMs = 0L
        var productivityTimeMs = 0L
        
        // Simplified categorization logic
        val socialPackages = setOf("com.facebook.katana", "com.instagram.android", "com.twitter.android", "com.zhiliaoapp.musically")
        val productivityPackages = setOf("com.google.android.apps.docs", "com.microsoft.office.word", "com.slack")

        stats.forEach { (packageName, usageStat) ->
            val timeInForeground = usageStat.totalTimeInForeground
            totalScreenTimeMs += timeInForeground
            
            if (socialPackages.contains(packageName)) {
                socialTimeMs += timeInForeground
            } else if (productivityPackages.contains(packageName)) {
                productivityTimeMs += timeInForeground
            }
        }

        // Night usage estimation (11 PM - 5 AM) - Requires more complex logic for exactness,
        // here we use a simplified proportion or mock for MVP.
        val nightUsageMs = (totalScreenTimeMs * 0.15).toLong() 

        val sharedPrefs = context.getSharedPreferences("wellness_wave_prefs", Context.MODE_PRIVATE)
        var userId = sharedPrefs.getString("user_id", null)
        if (userId == null) {
            userId = UUID.randomUUID().toString()
            sharedPrefs.edit().putString("user_id", userId).apply()
        }

        val df = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = df.format(Date())

        return com.example.myapplication.models.UsageMetrics(
            user_id = userId,
            date = dateString,
            screen_time = TimeUnit.MILLISECONDS.toMinutes(totalScreenTimeMs),
            unlock_count = (totalScreenTimeMs / (1000 * 60 * 5)).toInt(), // Mocked unlock count based on screen time
            social_time = TimeUnit.MILLISECONDS.toMinutes(socialTimeMs),
            productivity_time = TimeUnit.MILLISECONDS.toMinutes(productivityTimeMs),
            night_usage = TimeUnit.MILLISECONDS.toMinutes(nightUsageMs),
            session_count = 12, // Mocked
            mood_score = moodScore
        )
    }
}
