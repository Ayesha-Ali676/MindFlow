package com.example.myapplication.models

data class PredictionResponse(
    val user_id: String,
    val date_evaluated: String?,
    val predicted_stress_category: String,
    val confidence_score: Float,
    val real_time_feedback: String
)
