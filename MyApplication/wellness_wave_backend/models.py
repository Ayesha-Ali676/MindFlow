from pydantic import BaseModel, Field, validator
from typing import Optional

class DailyMetrics(BaseModel):
    user_id: str
    date: str  # YYYY-MM-DD
    screen_time: float = Field(..., ge=0)
    unlock_count: int = Field(..., ge=0)
    social_time: float = Field(..., ge=0)
    productivity_time: float = Field(..., ge=0)
    night_usage: float = Field(..., ge=0)
    session_count: int = Field(..., ge=0)
    scrolling_speed_avg: float = Field(..., ge=0)
    usage_consistency_shift: float = Field(..., ge=0)
    mood_score: int = Field(..., ge=1, le=10)

    @validator('mood_score')
    def mood_score_range(cls, v):
        if not 1 <= v <= 10:
            raise ValueError('mood_score must be between 1 and 10')
        return v
