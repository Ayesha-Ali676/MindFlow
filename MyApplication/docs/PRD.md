# Product Requirements Document (PRD)

## 1. Product Overview
- **Product Name:** Wellness Wave
- **Product Type:** Mobile-based behavioral wellness monitoring system.
- **Vision:** To help users become aware of their stress and digital burnout patterns by analyzing phone usage behavior and daily self-reported mood.

### Problem Statement
- Stress and digital burnout are increasing.
- Most mental health apps rely only on manual mood tracking.
- There is no simple app that correlates behavioral phone usage with daily stress levels.

### Proposed Solution
A mobile app that:
- Collects daily phone usage metrics.
- Collects daily self-reported stress score.
- Uses Machine Learning to detect stress patterns.
- Displays trends and alerts.
> ⚠️ **This app does NOT diagnose medical conditions.**

---

## 2. Goals & Objectives

### Primary Goal
Build an end-to-end working system that:
1. Collects behavioral phone usage data.
2. Learns the correlation with stress levels.
3. Displays stress trends inside the app.

### Success Criteria
- At least 300–500 real data samples collected.
- ML model trained with measurable performance.
- Working prediction displayed in the app.
- Stress trend visualization functional.

---

## 3. Scope

### ✅ In Scope (V1)
**Android app (Native Kotlin)**
- Daily screen time tracking
- Unlock count tracking
- App category tracking (social/productive)
- **Scrolling Behaviour** (Scroll speed, duration, time of day)
- **Usage Consistency** (Sudden increase/decrease in usage)
- Night usage detection
- Daily mood input (1–10 scale)

**Backend (FastAPI)**
- Random Forest ML model
- Stress score output (Low/Medium/High)
- Trend graph (7-day view)
- **Real-Time Feedback** - Friendly, actionable suggestions:
  - "You’ve been scrolling a lot tonight. Take a 2-minute breathing break."
  - "Focus seems low today. Try a short walk."
  - (Never a medical diagnosis. Notifications triggered immediately)

### ❌ Out of Scope (V1)
- iOS support
- Clinical depression diagnosis
- Voice analysis
- Typing behavior tracking
- LSTM / Deep Learning models
- Chatbot
- Therapist integration
- Play Store publishing

---

## 4. Target Users
### Primary Users
- Students (18–30)
- Young professionals
- Heavy smartphone users

### User Characteristics
- Use phone >3 hours daily.
- Interested in self-improvement.
- Comfortable entering a daily mood score.

---

## 5. User Flow
1. User installs app.
2. User grants usage permission.
3. App runs in background.
4. At 9 PM, user receives mood notification.
5. User selects stress level (1–10).
6. App aggregates daily metrics.
7. Data sent to backend.
8. Backend runs ML prediction.
9. Stress score shown in dashboard.
10. User views weekly trend.

---

## 6. Functional Requirements

### 6.1 Data Collection Module
- **FR-1:** Screen Time Tracking (App calculates total daily screen time).
- **FR-2:** Unlock Count Tracking (App records daily unlock frequency).
- **FR-3:** App Usage Categorization (Classify apps into Social, Productivity, Others).
- **FR-4:** Night Usage Tracking (Usage between 11 PM–5 AM).

### 6.2 Mood Input Module
- **FR-5:** Daily Mood Prompt (App sends daily notification at a configurable time).
- **FR-6:** Mood Scale (User selects stress level on a 1–10 scale).
- **FR-7:** Required Entry (Mood input required before daily record is finalized).

### 6.3 Backend Requirements
- **FR-8:** Data Storage (Backend stores daily aggregated metrics).
- **FR-9:** Prediction API (Backend exposes `GET /prediction` endpoint).
- **FR-10:** User Identification (System assigns a random UUID per user).

### 6.4 ML Requirements
- **FR-11:** Model Type (Random Forest Regressor or Classifier).
- **FR-12:** Input Features (`screen_time`, `unlock_count`, `social_ratio`, `night_usage_ratio`, `session_count`).
- **FR-13:** Output (`stress_score` 0–10 OR `stress_category` Low/Medium/High).

### 6.5 Dashboard Requirements
- **FR-14:** Stress Display (Show current stress score).
- **FR-15:** Trend Graph (Show 7-day trend line).
- **FR-16:** Alert System (If stress > threshold, display real-time actionable alert messages).

---

## 7. Non-Functional Requirements
- **Performance:** Prediction response time < 1 second.
- **Battery Usage:** App must not significantly increase battery drain.
- **Privacy:** No collection of Message content, Call recordings, Contact lists, Passwords.
- **Security:** All API calls over HTTPS.

---

## 8. Data Requirements
### Data Structure
Each daily entry must contain:
`user_id`, `date`, `screen_time`, `unlock_count`, `social_time`, `productivity_time`, `night_usage`, `session_count`, `mood_score`.

### Minimum Dataset Size
- 10–20 users
- 14–21 days
- Target: 300–500 rows

---

## 9. ML Evaluation Metrics
- **If Regression:** RMSE, MAE
- **If Classification:** Accuracy, F1-score
- **Target Performance:** ≥70% accuracy for stress category.

### Assumptions
- Users will enter mood daily.
- UsageStats API works reliably.
- Data collected is consistent.
- Behavioral patterns correlate with stress.

---

## 10. Risks
- Low user participation -> Poor model accuracy
- Missing mood labels -> Training failure
- Battery drain -> Users uninstall
- Small dataset -> Overfitting

---

## 11. Timeline (30–35 Days)
- **Phase 1:** Setup & Design (5 days)
- **Phase 2:** App + Backend (10 days)
- **Phase 3:** ML Development (10 days)
- **Phase 4:** Integration (5 days)
- **Phase 5:** Testing (5 days)

### Deliverables
- **Kotlin Android App**
- **FastAPI** Python backend (API + ML serving)
- Firebase database
- Trained ML model (.pkl)
- Working stress dashboard
- Dataset (CSV)

---

## Definition of Done
- [ ] User can install app
- [ ] App collects daily behavioral metrics
- [ ] User enters mood
- [ ] Backend stores data
- [ ] ML model predicts stress
- [ ] App displays stress trend

> **Final Clarification:** This product detects behavioral stress patterns and estimates stress risk. It does NOT diagnose depression or medical disorders.
