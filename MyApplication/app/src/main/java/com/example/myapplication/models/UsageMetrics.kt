package com.example.myapplication.models

data class UsageMetrics(
    val user_id: String,
    val date: String,
    val screen_time: Long, // in minutes
    val unlock_count: Int,
    val social_time: Long, // in minutes
    val productivity_time: Long, // in minutes
    val night_usage: Long, // in minutes
    val session_count: Int,
    val scrolling_speed_avg: Float = 0f,
    val usage_consistency_shift: Float = 0f,
    val mood_score: Int
)
