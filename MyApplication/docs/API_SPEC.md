# REST API Specification

**Base URL:** `http://localhost:8000` *(Substitute with actual production domain when deployed)*

## Global Headers
- `Content-Type: application/json`

---

## 1. Health Check
Checks if the server instance is operational.

**Endpoint:** `GET /health`

**Success Response (200 OK):**
```json
{
  "status": "active",
  "message": "Wellness Wave API is running smoothly."
}
```

---

## 2. Submit Daily Data
Receives raw behavioral metrics from the Android Native (Kotlin) application and persists the payload into the database.

**Endpoint:** `POST /daily-data`

**Request Payload:**
```json
{
  "user_id": "uuid-string",
  "date": "YYYY-MM-DD",
  "screen_time": 320,
  "unlock_count": 45,
  "social_time": 120,
  "productivity_time": 60,
  "night_usage": 45,
  "session_count": 12,
  "scrolling_speed_avg": 5.4,
  "usage_consistency_shift": 1.2,
  "mood_score": 7
}
```
*(All time values are typically recorded in minutes)*

**Success Response (201 Created):**
```json
{
  "status": "success",
  "message": "Daily metrics stored securely.",
  "data_id": 1054
}
```

**Error Response (422 Unprocessable Entity - Validation Failed):**
Returned if the schema constraint fails (e.g. string inputted where an integer was expected).
```json
{
  "detail": [
    {
      "loc": ["body", "mood_score"],
      "msg": "field required",
      "type": "value_error.missing"
    }
  ]
}
```

---

## 3. Get Stress Prediction
Triggers the Machine Learning Model to evaluate the most recent database records for a particular user.

**Endpoint:** `GET /prediction`

**Query Parameters:**
- `user_id` (string, required): The UUID of the specific client requiring the estimate.

**Success Response (200 OK):**
```json
{
  "user_id": "uuid-string",
  "date_evaluated": "YYYY-MM-DD",
  "predicted_stress_category": "Medium",
  "confidence_score": 0.85,
  "real_time_feedback": "Focus seems low today. Try a short walk."
}
```

**Error Response (404 Not Found):**
Returned if no metrics have been submitted for this user previously.
```json
{
  "detail": "Insufficient historical data found for the provided user to make a reliable prediction."
}
```
