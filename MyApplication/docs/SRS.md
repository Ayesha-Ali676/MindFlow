# Software Requirements Specification (SRS)
## 1. Introduction
### 1.1 Purpose
The purpose of this document is to specify the software requirements for the Wellness Wave application, a behavioral stress monitoring system. It details the functional and non-functional requirements to guide development, testing, and deployment.

### 1.2 Scope
Wellness Wave combines a Native Android mobile app (Kotlin) and a FastAPI-based Python backend. The system collects user screen time and interaction data along with a daily self-reported mood score. It uses a Scikit-learn Random Forest machine learning model to predict stress levels (Low, Medium, High). The backend integrates with a cloud database (Supabase PostgreSQL / Firebase Firestore) for data storage.
*Disclaimer: The application does NOT diagnose medical conditions.*

## 2. Overall Description
### 2.1 Product Perspective
Wellness Wave is an independent mobile application communicating directly with a centralized backend via RESTful APIs. 

### 2.2 System Architecture
- **Frontend App:** Native Android (Kotlin) utilizing `UsageStatsManager` for raw behavioral data.
- **Backend API:** FastAPI (Python) routing API requests and serving ML models.
- **Database:** Supabase PostgreSQL or Firebase Firestore (Free Tier).
- **ML Model:** Scikit-learn Random Forest model locally trained and deployed as a `.pkl` file.

## 3. Functional Requirements
### 3.1 Data Collection (Frontend)
- **FR1.1:** The mobile app shall collect daily usage data through Android's `UsageStatsManager`.
- **FR1.2:** The app shall require the user to provide the `PACKAGE_USAGE_STATS` permission.
- **FR1.3:** The collected metrics shall include: overall screen time, unlock count, social media time, productivity apps time, nighttime usage, total session count, scrolling behaviour (speed/duration), and usage consistency (sudden usage shifts).
- **FR1.4:** The mobile app shall prompt the user at 9:00 PM daily to input a self-reported mood score from 1 to 10.

### 3.2 Data Processing (Backend)
- **FR2.1:** The backend shall provide a `POST /daily-data` endpoint to receive behavioral metrics and mood score from the app.
- **FR2.2:** The backend shall validate the received payload schema using Pydantic.
- **FR2.3:** The backend shall persist validated data securely into the configured cloud database (Supabase `daily_metrics` table or Firebase Firestore).

### 3.3 Stress Prediction
- **FR3.1:** The backend shall provide a `GET /prediction` endpoint that evaluates the user's latest data.
- **FR3.2:** A trained Scikit-learn Random Forest model shall analyze the data to categorize user stress as 'Low', 'Medium', or 'High'.
- **FR3.3:** The machine learning model must be retrained only when a minimum threshold of 300 data rows is collected across the user base.

### 3.4 Data Visualization & Alerts (Frontend)
- **FR4.1:** The mobile app dashboard shall display the most recent predicted stress score (Low, Medium, or High).
- **FR4.2:** The mobile app shall display a responsive 7-day trend line chart mapping the user's stress progression.
- **FR4.3:** The mobile app shall display real-time actionable feedback alerts when usage behavior exceeds specific stress thresholds.

## 4. Non-Functional Requirements
### 4.1 Performance & Scalability
- **NFR1:** The API responses for data submission and prediction shall take under 500ms on average under regular load.
- **NFR2:** The App shall safely process background jobs using `workmanager` without depleting device battery reserves.

### 4.2 Security & Privacy
- **NFR3:** All API communications shall occur over HTTPS.
- **NFR4:** No explicit Medical claims or diagnoses shall be shown to the user on the UI.
- **NFR5:** Database authentication keys (either `.env` for Supabase or `firebase_key.json` for Firebase) shall be restricted and must not end up in version control.

### 4.3 Reliability
- **NFR6:** The backend API shall stay highly available (uptime 99%) by utilizing free-tier auto-recovery systems.
- **NFR7:** System crashes in the mobile app shall trigger a safe recovery fallback state.
