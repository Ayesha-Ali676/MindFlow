# COMPLETE STEP-BY-STEP IMPLEMENTATION PLAN
**(Flutter + FastAPI + Firebase + Scikit-learn)**

## 🔷 STAGE 0 — PROJECT CLARITY (DO NOT SKIP)
Before coding:

### 0.1 Define Final MVP Scope
**You are building:**
- Android-only app
- Collects usage behavior
- Collects daily stress score (1–10)
- Sends to backend
- Predicts stress using ML
- Shows 7-day trend

**Not building:**
- iOS
- Deep learning
- Real-time tracking
- Clinical diagnosis

*Lock scope.*

### 0.2 Finalize Architecture
- Mobile (Native Android / Kotlin)
- → FastAPI backend
- → Firebase Firestore
- → Random Forest model

*Confirm this is final.*

---

## 🔷 STAGE 1 — DEVELOPMENT ENVIRONMENT SETUP

### 1.1 Install Core Tools
**Install:**
- Android Studio
- Kotlin Plugin
- Python 3.10+
- Git
- Postman (API testing)

### 1.2 Create Project Structure
Create two separate folders:
```
wellness-wave-app/
wellness-wave-backend/
```
*Initialize Git in both.*

---

## 🔷 STAGE 2 — BACKEND DEVELOPMENT (BUILD FIRST)
*We build backend first so mobile has a target.*

### 2.1 Create Python Virtual Environment
Inside backend folder:
```bash
python -m venv venv
source venv/bin/activate
```
**Install dependencies:**
```bash
pip install fastapi uvicorn firebase-admin pydantic pandas numpy scikit-learn joblib python-dotenv
```

### 2.2 Create Basic FastAPI App
**Create `main.py`**
Add:
- FastAPI instance
- `GET /health` endpoint

**Run:**
```bash
uvicorn main:app --reload
```
*Test in browser.*

### 2.3 Setup Firebase
1. Go to Firebase Console
2. Create new project
3. Enable Firestore
4. Download service account key
5. Place JSON in backend folder
6. Initialize Firebase in code using `firebase-admin`.
7. Test writing dummy document to Firestore.

### 2.4 Define Data Schema (Critical)
**Create Pydantic model:**
Fields:
- `user_id` (string)
- `date` (string)
- `screen_time` (float)
- `unlock_count` (int)
- `social_time` (float)
- `productivity_time` (float)
- `night_usage` (float)
- `session_count` (int)
- `mood_score` (int)

**Add validation:**
- `mood_score` between 1 and 10
- no negative values

### 2.5 Create POST `/daily-data`
**Steps:**
1. Accept request body
2. Validate using Pydantic
3. Store in Firestore: `users/{user_id}/daily_metrics/{date}`
4. Return JSON success response

*Test using Postman. Confirm Firestore document created.*

### 2.6 Create GET `/prediction` (Temporary)
**Return static response for now:**
```json
{
  "predicted_stress": 5.5,
  "category": "Medium"
}
```
*This allows frontend integration before ML.*

---

## 🔷 STAGE 3 — MOBILE APP DEVELOPMENT

### 3.1 Create Android Project
1. Open Android Studio
2. File > New > New Project > "Empty Views Activity"
3. Name: `wellness_wave`
4. Language: **Kotlin**

**Organize structure:**
```
package com.example.wellness_wave
 ├── api/           (Retrofit Interfaces)
 ├── ui/            (Activities & Fragments)
 ├── models/        (Data Classes)
 ├── services/      (WorkManager & native services)
 └── MainActivity.kt
```
*Run basic app.*

### 3.2 Add Required Android Permissions
Edit `AndroidManifest.xml`:
**Add:**
- `PACKAGE_USAGE_STATS`
- `FOREGROUND_SERVICE`
- `POST_NOTIFICATIONS`
- `INTERNET`

*Also guide user to manually enable usage access in settings using a Settings Intent.*

### 3.3 Implement Usage Data Collection
*This is the core native integration.*
**Steps:**
1. Use `UsageStatsManager` in Kotlin.
2. Fetch:
   - Total screen time
   - Unlock count
   - App usage time
3. Print results in Logcat / update UI.

*Test thoroughly.*

### 3.4 Create Daily Aggregation Logic
Inside Kotlin `ViewModel` or `Repository`:
**Create function:** `calculateDailyMetrics()`
Compute:
- `total_screen_time`
- `unlock_count`
- `social_time` (filter app categories by package name)
- `night_usage` (usage between 23:00–05:00)
- `session_count`

*Store temporarily using Room Database or DataStore.*

### 3.5 Create Mood Input Screen
**UI components (XML or Compose):**
- Slider (1–10)
- Submit button
- Validation (must select value)

*Store mood locally.*

---

## 🔷 STAGE 4 — CONNECT MOBILE TO BACKEND

### 4.1 Add Retrofit Package
Add to `build.gradle` (app-level):
```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```
Create `ApiService` interface.

### 4.2 Send Daily Data to Backend
**On mood submit:**
1. Collect metrics
2. Build JSON body
3. Send POST request
4. Handle success/error

*Verify Firestore receives data.*

### 4.3 Fetch Prediction
1. Call `GET /prediction`.
2. Display result in UI.

*Now full pipeline works (dummy prediction).*

---

## 🔷 STAGE 5 — REAL DATA COLLECTION
- **Duration:** 2–3 weeks.
- Use app daily.
- **Collect minimum:** 300+ rows
- **Monitor:** Missing fields, Extreme values, Consistency
- Export Firestore data as CSV.

---

## 🔷 STAGE 6 — MACHINE LEARNING DEVELOPMENT

### 6.1 Data Cleaning
Using Pandas:
- Remove null rows
- Remove `screen_time < 5` minutes
- Remove `unlock_count` unrealistic

### 6.2 Feature Engineering
Create:
- `social_ratio`
- `night_ratio`
- `unlock_intensity`
- `avg_session_length`

*These become model inputs.*

### 6.3 Train Model
Split data: 80% train / 20% test
Train Random Forest.
**Evaluate:** MAE, RMSE, R²

### 6.4 Hyperparameter Tuning
Use GridSearchCV.
**Select best:** `n_estimators`, `max_depth`, `min_samples_split`.

### 6.5 Save Model
```python
joblib.dump(model, "stress_model.pkl")
```

---

## 🔷 STAGE 7 — ML INTEGRATION INTO BACKEND

### 7.1 Load Model at Startup
In FastAPI: Load `stress_model.pkl` once.

### 7.2 Replace Dummy Prediction Logic
Inside `GET /prediction`:
1. Fetch latest user data
2. Create feature vector
3. Run `model.predict()`
4. Return result

*Test using Postman.*

### 7.3 Test with Mobile App
App should now show real ML output. Validate manually.

---

## 🔷 STAGE 8 — DASHBOARD IMPLEMENTATION

### 8.1 Add MPAndroidChart
Install package via Gradle.

### 8.2 Create Trend Screen
Fetch last 7 days from backend via Retrofit. Display line chart.

### 8.3 Add Stress Category Mapping
Define thresholds:
- 1–3 → Low
- 4–6 → Medium
- 7–10 → High

*Display colored indicator.*

---

## 🔷 STAGE 9 — STABILITY HARDENING

### 9.1 Add Error Handling
**Mobile:** Retry failed API, Show user-friendly errors
**Backend:** Catch exceptions, Log invalid payloads

### 9.2 Optimize Performance
- Ensure model loads once
- Avoid redundant database calls
- Reduce network payload size

### 9.3 Test Background Job
Ensure WorkManager triggers daily aggregation reliably.

---

## 🔷 STAGE 10 — FINAL VALIDATION
Run system for 7 days continuously.
**Verify:**
- [x] Data collected daily
- [x] Backend stable
- [x] Predictions accurate
- [x] Dashboard updates
- [x] No crashes

---

## 🎯 FINAL RESULT
You will have:
- Native Android App (Kotlin)
- FastAPI backend
- Firebase database
- Random Forest ML model
- Real stress prediction
- Trend dashboard
- Production-grade MVP.

## 🧠 Important Discipline Rules
- **Never train ML before real data.**
- **Never skip validation.**
- **Never integrate ML before backend stable.**
- **Always test with Postman before connecting mobile.**
- **Keep architecture simple.**
