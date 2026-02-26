from fastapi import APIRouter, HTTPException
from wellness_wave_backend.database import get_latest_metrics

router = APIRouter()

@router.get("/prediction")
async def get_prediction(user_id: str):
    latest_data = get_latest_metrics(user_id)
    
    # Mock prediction logic for now
    if latest_data:
        mood = latest_data.get("mood_score", 5)
        if mood <= 3:
            category = "High"
            feedback = "Stress levels seem high. Please take a break and practice some mindfulness."
        elif mood <= 6:
            category = "Medium"
            feedback = "You're doing okay, but remember to take short walks to stay focused."
        else:
            category = "Low"
            feedback = "Great job! Your wellness levels are looking good."
            
        return {
            "user_id": user_id,
            "date_evaluated": latest_data.get("date"),
            "predicted_stress_category": category,
            "confidence_score": 0.85,
            "real_time_feedback": feedback
        }
    
    # Fallback if no data found
    return {
        "user_id": user_id,
        "predicted_stress_category": "Medium",
        "confidence_score": 0.5,
        "real_time_feedback": "Insufficient data for a precise prediction. Keep tracking your mood!"
    }
