from fastapi import FastAPI
from wellness_wave_backend.api import data, prediction

app = FastAPI(title="Wellness Wave API")

@app.get("/health")
async def health_check():
    return {
        "status": "active",
        "message": "Wellness Wave API is running smoothly."
    }

# Include routers
app.include_router(data.router, tags=["Data Collection"])
app.include_router(prediction.router, tags=["Stress Prediction"])
