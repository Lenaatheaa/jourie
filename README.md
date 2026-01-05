# 📔 Jourie - Mental Health Journaling App

<div align="center">

![Jourie Logo](logo/logo.png)

**Your Personal Mental Health Companion**

A modern Android journaling app powered by AI to help you track your emotions, maintain daily streaks, and improve your mental wellness.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-green.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-10.0+-orange.svg?style=flat&logo=firebase)](https://firebase.google.com)
[![Gemini AI](https://img.shields.io/badge/Gemini%20AI-2.5%20Flash-blue.svg?style=flat&logo=google)](https://ai.google.dev)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>

---

## Table of Contents

- [About](#-about)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [API Configuration](#-api-configuration)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## About

**Jourie** is a comprehensive mental health journaling application that combines the power of AI with gamification to help users:

- **Journal Daily** - Write and reflect on your thoughts and feelings
- **AI Analysis** - Get instant emotional insights powered by Google Gemini AI
- **Track Streaks** - Build consistent journaling habits with daily streak tracking
- **Pet Evolution** - Watch your companion pet evolve as you maintain your streak
- **Earn Achievements** - Unlock badges for reaching journaling milestones
- **Visualize Emotions** - See your emotional patterns through beautiful charts
- **Get Recommendations** - Receive personalized wellness suggestions

The app uses **Google Gemini AI** to analyze journal entries and provide:
- Emotional distribution across 18+ emotions
- Root cause analysis of your feelings
- Personalized wellness recommendations
- Inspirational quotes tailored to your mood

---

## Features

### **Authentication**
- Secure user registration and login with Firebase Authentication
- Profile management with customizable user information
- Password validation and error handling

### **Journaling**
- **Rich Text Editor** - Write your thoughts with an intuitive interface
- **Mood Selection** - Choose from 10+ mood options (Happy, Sad, Calm, Anxious, etc.)
- **Auto-Save** - Never lose your entries
- **Date Tracking** - Automatic timestamp for each entry

### **AI-Powered Analysis**
- **Emotion Detection** - Analyze 18 different emotions in your writing
- **Sentiment Analysis** - Understand the overall tone of your entry
- **Root Cause Identification** - Discover what's driving your emotions
- **Wellness Recommendations** - Get actionable advice for mental health
- **Inspirational Quotes** - Receive motivational messages
- **Visual Charts** - See emotion distribution in beautiful donut charts

### **Streak System**
- **Daily Tracking** - Maintain your journaling streak
- **Pet Evolution** - Your companion evolves through 4 stages:
  - **Level 1**: Capybara (1 day)
  - **Level 2**: Capybara (3 days)
  - **Level 3**: Bear (7 days)
  - **Level 4**: Dragon (14 days)
- **Progress Visualization** - Track your journey to the next level
- **Streak Recovery** - Automatic reset if you miss a day

### **Achievement System**
- **Streak Badges** (6 levels):
  - Level 1: 3 days
  - Level 2: 7 days
  - Level 3: 14 days
  - Level 4: 21 days
  - Level 5: 30 days
  - Level 6: 60 days

- **Journal Badges** (5 levels):
  - Level 1: 1 journal
  - Level 2: 20 journals
  - Level 3: 50 journals
  - Level 4: 100 journals
  - Level 5: 200 journals

### **History & Management**
- **Search Functionality** - Find entries by content, mood, or date
- **Filter Options** - Sort and organize your journals
- **Delete Entries** - Remove unwanted journals (with cascade delete)
- **Relative Dates** - See entries as "Today", "Yesterday", "2 days ago", etc.

### **Beautiful UI/UX**
- **Material 3 Design** - Modern, clean interface
- **Floating Navigation** - Smooth bottom navigation bar
- **Custom Animations** - Delightful micro-interactions
- **Dark/Light Theme** - Comfortable viewing in any environment
- **Responsive Layout** - Adapts to different screen sizes

---

## Screenshots

<div align="center">

| Login | Dashboard | Add Journal |
|:---:|:---:|:---:|
| ![Login](screenshots/login.png) | ![Dashboard](screenshots/dashboard.png) | ![Add Journal](screenshots/add_journal.png) |

| AI Analysis | Streak | Achievements |
|:---:|:---:|:---:|
| ![Analysis](screenshots/analysis.png) | ![Streak](screenshots/streak.png) | ![Achievements](screenshots/achievements.png) |

| History | Profile | Edit Profile |
|:---:|:---:|:---:|
| ![History](screenshots/history.png) | ![Profile](screenshots/profile.png) | ![Edit](screenshots/edit_profile.png) |

</div>

---

## Tech Stack

### **Core Technologies**
- **Language**: [Kotlin](https://kotlinlang.org/) 1.9+
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14+)
- **Build System**: Gradle with Kotlin DSL

### **Architecture & Patterns**
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Manual DI (Repository Pattern)
- **State Management**: Kotlin Flow + StateFlow
- **Navigation**: Jetpack Navigation Compose
- **Async Operations**: Kotlin Coroutines

### **Firebase Services**
- **Authentication**: Firebase Auth
- **Database**: Cloud Firestore
- **BoM Version**: 33.2.0

### **AI & Machine Learning**
- **AI Provider**: Google Generative AI
- **Model**: Gemini 2.5 Flash
- **SDK Version**: 0.9.0

### **Additional Libraries**
- **Image Loading**: Coil Compose 2.6.0 (with GIF support)
- **Splash Screen**: Core Splash Screen API 1.0.1
- **Lifecycle**: ViewModel Compose 2.10.0
- **Coroutines**: Play Services 1.8.1

---

## Architecture

Jourie follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│                   Presentation Layer                     │
│  ┌─────────────┐  ┌──────────────┐  ┌───────────────┐  │
│  │   Screen    │→ │  ViewModel   │→ │     State     │  │
│  │ (Composable)│  │ (StateFlow)  │  │ (Data Class)  │  │
│  └─────────────┘  └──────────────┘  └───────────────┘  │
└─────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────┐
│                     Domain Layer                         │
│  ┌──────────────────────────────────────────────────┐   │
│  │              Use Cases (Business Logic)          │   │
│  │  • GetMainDashboardDataUseCase                   │   │
│  │  • GetStreakDataUseCase                          │   │
│  │  • CalculateEvolutionProgressUseCase             │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────┐
│                      Data Layer                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Repository   │→ │  Firebase    │→ │    Model     │  │
│  │              │  │  Firestore   │  │ (Data Class) │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### **Key Architectural Decisions**

1. **MVVM Pattern**: Each screen has its own ViewModel for state management
2. **Unidirectional Data Flow**: State flows from ViewModel to UI
3. **Repository Pattern**: Abstracts data sources (Firestore, Gemini API)
4. **Use Cases**: Encapsulate complex business logic
5. **Reactive Programming**: StateFlow for reactive UI updates

---

## Getting Started

### **Prerequisites**

- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or later
- Android SDK with API 24+
- Firebase account
- Google AI Studio account (for Gemini API)

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/jourie.git
   cd jourie
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Configure Firebase**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com)
   - Add an Android app to your Firebase project
   - Download `google-services.json`
   - Place it in `app/` directory
   - Enable **Authentication** (Email/Password)
   - Enable **Cloud Firestore**

4. **Configure Gemini API**
   - Get your API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Open `app/build.gradle.kts`
   - Replace the API key:
     ```kotlin
     buildConfigField("String", "GEMINI_API_KEY", "\"YOUR_API_KEY_HERE\"")
     ```

5. **Sync and Build**
   ```bash
   ./gradlew build
   ```

6. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
     ```bash
     ./gradlew installDebug
     ```

### **Configuration Files**

Create a `local.properties` file in the root directory (if not exists):
```properties
sdk.dir=YOUR_ANDROID_SDK_PATH
```

---

## Project Structure

```
Jourie/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/jourie/
│   │   │   │   ├── MainActivity.kt                    # App entry point
│   │   │   │   │
│   │   │   │   ├── data/                              # Data Layer
│   │   │   │   │   ├── firebase/
│   │   │   │   │   │   └── FirebaseAuthRepository.kt  # Auth & Profile
│   │   │   │   │   ├── model/                         # Data models
│   │   │   │   │   │   ├── JournalEntry.kt
│   │   │   │   │   │   ├── NewJournal.kt
│   │   │   │   │   │   ├── StreakData.kt
│   │   │   │   │   │   ├── Badge.kt
│   │   │   │   │   │   └── ...
│   │   │   │   │   └── repository/                    # Data repositories
│   │   │   │   │       ├── NewJournalRepository.kt    # Journal CRUD
│   │   │   │   │       ├── JournalRepository.kt       # History
│   │   │   │   │       ├── StreakRepository.kt        # Streak logic
│   │   │   │   │       ├── MilestonesRepository.kt    # Achievements
│   │   │   │   │       └── ...
│   │   │   │   │
│   │   │   │   ├── domain/                            # Domain Layer
│   │   │   │   │   └── usecase/                       # Business logic
│   │   │   │   │       ├── GetMainDashboardDataUseCase.kt
│   │   │   │   │       ├── GetStreakDataUseCase.kt
│   │   │   │   │       ├── CalculateEvolutionProgressUseCase.kt
│   │   │   │   │       └── ...
│   │   │   │   │
│   │   │   │   ├── presentation/                      # Presentation Layer
│   │   │   │   │   ├── auth/                          # Authentication
│   │   │   │   │   │   ├── login/
│   │   │   │   │   │   │   ├── UserLoginScreen.kt
│   │   │   │   │   │   │   ├── UserLoginViewModel.kt
│   │   │   │   │   │   │   └── UserLoginState.kt
│   │   │   │   │   │   └── register/
│   │   │   │   │   │
│   │   │   │   │   ├── dashboard/                     # Main dashboard
│   │   │   │   │   │   ├── MainDashboardScreen.kt
│   │   │   │   │   │   ├── MainDashboardViewModel.kt
│   │   │   │   │   │   ├── MainDashboardState.kt
│   │   │   │   │   │   └── components/
│   │   │   │   │   │
│   │   │   │   │   ├── journal/
│   │   │   │   │   │   ├── add/                       # Add journal
│   │   │   │   │   │   │   ├── AddNewJournalScreen.kt
│   │   │   │   │   │   │   ├── AddNewJournalViewModel.kt
│   │   │   │   │   │   │   └── components/
│   │   │   │   │   │   └── analysis/                  # AI analysis
│   │   │   │   │   │       ├── JournalAnalysisScreen.kt
│   │   │   │   │   │       ├── JournalAnalysisViewModel.kt
│   │   │   │   │   │       ├── GeminiAiRepository.kt
│   │   │   │   │   │       └── components/
│   │   │   │   │   │
│   │   │   │   │   ├── streak/                        # Streak tracking
│   │   │   │   │   ├── achievements/                  # Badges
│   │   │   │   │   ├── history/                       # Journal history
│   │   │   │   │   ├── profile/                       # User profile
│   │   │   │   │   └── edit_profile/                  # Edit profile
│   │   │   │   │
│   │   │   │   ├── navigation/                        # Navigation
│   │   │   │   │   ├── navGraph.kt                    # Main nav graph
│   │   │   │   │   └── AuthNavGraph.kt                # Auth nav graph
│   │   │   │   │
│   │   │   │   └── ui/theme/                          # UI Theme
│   │   │   │       ├── Color.kt                       # Color palette
│   │   │   │       ├── Theme.kt                       # App theme
│   │   │   │       ├── Type.kt                        # Typography
│   │   │   │       └── Shape.kt                       # Shapes
│   │   │   │
│   │   │   ├── res/                                   # Resources
│   │   │   │   ├── drawable/
│   │   │   │   ├── mipmap-*/
│   │   │   │   ├── values/
│   │   │   │   └── xml/
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   └── test/                                      # Unit tests
│   │
│   ├── build.gradle.kts                               # App-level Gradle
│   └── google-services.json                           # Firebase config
│
├── gradle/                                            # Gradle wrapper
├── build.gradle.kts                                   # Project-level Gradle
├── settings.gradle.kts                                # Gradle settings
├── gradle.properties                                  # Gradle properties
├── DATABASE_STRUCTURE.md                              # Database documentation
├── DATABASE_TABLES_FORMAT.md                          # Database schema
└── README.md                                          # This file
```

---

## Database Schema

Jourie uses **Cloud Firestore** with a hierarchical structure:

```
Firestore Root
└── users/ (Collection)
    └── {uid}/ (Document - Firebase Auth UID)
        ├── fullName: String
        ├── email: String
        ├── phone: String?
        ├── dob: String?
        ├── createdAt: Long
        ├── currentDayStreak: Int
        ├── lastJournalDate: String (yyyy-MM-dd)
        ├── currentPetLevel: Int (1-4)
        ├── totalJournalDays: Int
        ├── highestEvolutionLevelAchieved: Int
        │
        └── journals/ (SubCollection)
            └── {journalId}/ (Document - Auto ID)
                ├── content: String
                ├── createdAt: Timestamp
                ├── day: Int
                ├── month: String
                ├── mood: String
                ├── dateTimestamp: Long
                │
                ├── aiAnalysis/ (SubCollection)
                │   └── {analysisId}/ (Document)
                │       ├── dominantEmotion: String
                │       ├── predictionSummary: String
                │       ├── quote: String
                │       ├── recommendation: String
                │       ├── rootCause: String
                │       └── createdAt: Timestamp
                │
                └── emotionScores/ (SubCollection)
                    └── {scoreId}/ (Document - Multiple)
                        ├── emotionName: String
                        ├── score: Int (0-100)
                        ├── colorHex: String
                        └── comparisonTrend: Int
```

**Key Features**:
- **3-level depth**: users → journals → aiAnalysis/emotionScores
- **Cascade delete**: Deleting a journal removes all subcollections
- **Real-time sync**: Changes sync across devices instantly
- **Offline support**: Firestore caches data locally

For detailed schema documentation, see [DATABASE_STRUCTURE.md](DATABASE_STRUCTURE.md)

---

## 🔑 API Configuration

### **Gemini AI API**

1. **Get API Key**:
   - Visit [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Create a new API key
   - Copy the key

2. **Configure in Project**:
   - Open `app/build.gradle.kts`
   - Find the `buildConfigField` line:
     ```kotlin
     buildConfigField("String", "GEMINI_API_KEY", "\"YOUR_API_KEY_HERE\"")
     ```
   - Replace `YOUR_API_KEY_HERE` with your actual key

3. **Usage in Code**:
   ```kotlin
   // Accessed via BuildConfig
   val apiKey = BuildConfig.GEMINI_API_KEY
   ```

### **Firebase Configuration**

1. **Setup Firebase Project**:
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create a new project
   - Add an Android app

2. **Enable Services**:
   - **Authentication**: Enable Email/Password provider
   - **Firestore**: Create database in production mode
   - **Security Rules**: See [Firestore Security Rules](#firestore-security-rules)

3. **Download Config**:
   - Download `google-services.json`
   - Place in `app/` directory

### **Firestore Security Rules**

Add these rules to your Firestore for security:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
      
      match /journals/{journalId} {
        allow read, write: if request.auth != null && request.auth.uid == userId;
        
        match /aiAnalysis/{analysisId} {
          allow read, write: if request.auth != null && request.auth.uid == userId;
        }
        
        match /emotionScores/{scoreId} {
          allow read, write: if request.auth != null && request.auth.uid == userId;
        }
      }
    }
  }
}
```

## Tips & Best Practices

### **For Users**
- Journal daily for best results
- Be honest and detailed in your entries
- Review AI insights regularly
- Set daily reminders to maintain streaks
- Explore different moods to track patterns

### **For Developers**
- Always test with real Firebase data
- Monitor Gemini API usage and costs
- Implement proper error handling
- Use Firestore offline persistence
- Follow Material 3 design guidelines
- Keep dependencies up to date

---

