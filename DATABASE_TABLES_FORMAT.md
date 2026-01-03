# 📊 STRUKTUR DATABASE FIRESTORE - JOURIE APP (FORMAT TABEL)

## 📌 BERDASARKAN EXISTING CODE IMPLEMENTATION

---

## **Tabel Pengguna | Collection: `users` | Primary Key: `uid`**

Koleksi ini menyimpan profil dasar pengguna dan data gamifikasi (streak & pet level) yang diambil dari FirebaseAuthRepository dan StreakRepository.

| Nama Field | Tipe Data | Keterangan |
|------------|-----------|------------|
| uid | String | Unique ID dari Firebase Auth (sebagai Document ID) |
| fullName | String | Nama lengkap pengguna |
| email | String | Alamat email pengguna |
| phone | String (nullable) | Nomor telepon pengguna (opsional) |
| dob | String (nullable) | Tanggal lahir pengguna (opsional, format bebas) |
| createdAt | Number (Long) | Timestamp saat akun dibuat (milliseconds) |
| currentDayStreak | Number (Int) | Jumlah hari berturut-turut user menulis jurnal |
| lastJournalDate | String | Tanggal terakhir user menulis jurnal (format: yyyy-MM-dd) |
| currentPetLevel | Number (Int) | Level evolusi Pet saat ini (1-4: Capybara→Bear→Dragon) |
| totalJournalDays | Number (Int) | Total hari user pernah menulis jurnal (tidak harus berturut-turut) |
| highestEvolutionLevelAchieved | Number (Int) | Level evolusi tertinggi yang pernah dicapai user |

**File Terkait:**
- `FirebaseAuthRepository.kt` (line 14-30 untuk register, line 55-70 untuk read profile)
- `StreakRepository.kt` (line 32-61 untuk read streak, line 72-128 untuk update streak)

---

## **Tabel Jurnal | Path: `users/{uid}/journals` | Primary Key: `journalId`**

SubCollection yang menyimpan semua jurnal harian yang ditulis oleh user. Setiap dokumen merepresentasikan satu entri jurnal.

| Nama Field | Tipe Data | Keterangan |
|------------|-----------|------------|
| journalId | String | Auto-generated ID dari Firestore (sebagai Document ID) |
| content | String | Isi teks jurnal harian yang ditulis user |
| createdAt | Timestamp | Server timestamp saat jurnal dibuat (FieldValue.serverTimestamp()) |
| day | Number (Int) | Tanggal dalam bulan (1-31) untuk display UI |
| month | String | Singkatan nama bulan (Jan, Feb, Mar, Apr, dst.) untuk display UI |
| mood | String | Mood yang dipilih user saat menulis (Happy, Sad, Calm, Anxious, Angry, Neutral, dll.) |
| dateTimestamp | Number (Long) | Timestamp lokal sebagai backup (milliseconds) |

**File Terkait:**
- `NewJournalRepository.kt` (line 35-66 untuk insert journal)
- `JournalRepository.kt` (line 71-106 untuk read all journals, line 115-152 untuk delete journal)

---

## **Tabel Data Analisis AI | Path: `users/{uid}/journals/{journalId}/aiAnalysis` | Primary Key: `analysisId`**

SubCollection yang menyimpan hasil analisis AI dari Gemini API untuk setiap jurnal. Biasanya hanya ada 1 dokumen per jurnal.

| Nama Field | Tipe Data | Keterangan |
|------------|-----------|------------|
| analysisId | String | Auto-generated ID dari Firestore (sebagai Document ID) |
| dominantEmotion | String | Emosi dominan yang terdeteksi AI dari jurnal (Joy, Sadness, Anger, Fear, dll.) |
| predictionSummary | String | Ringkasan prediksi AI tentang kondisi emosional user ke depan |
| quote | String | Quote inspiratif yang diberikan AI berdasarkan analisis jurnal |
| recommendation | String | Rekomendasi wellness/tindakan yang disarankan AI untuk user |
| rootCause | String | Akar penyebab emosi yang terdeteksi AI dari analisis jurnal |
| createdAt | Timestamp | Server timestamp saat analisis AI dibuat (FieldValue.serverTimestamp()) |

**File Terkait:**
- `NewJournalRepository.kt` (line 90-114 untuk save AI analysis, line 178-201 untuk read latest analysis)

---

## **Tabel Skor Emosi | Path: `users/{uid}/journals/{journalId}/emotionScores` | Primary Key: `scoreId`**

SubCollection yang menyimpan detail skor untuk setiap emosi yang terdeteksi AI. Satu jurnal bisa memiliki 5-7 dokumen emotion scores (untuk berbagai emosi seperti Joy, Sadness, Anger, Fear, Surprise, Disgust, Neutral).

| Nama Field | Tipe Data | Keterangan |
|------------|-----------|------------|
| scoreId | String | Auto-generated ID dari Firestore (sebagai Document ID) |
| emotionName | String | Nama emosi spesifik (Joy, Sadness, Anger, Fear, Surprise, Disgust, Neutral, dll.) |
| score | Number (Int) | Skor intensitas emosi (0-100, semakin tinggi semakin dominan) |
| colorHex | String | Kode warna hex untuk visualisasi emosi di UI (contoh: #FFD700 untuk Joy) |
| comparisonTrend | Number (Int) | Tren perbandingan dengan jurnal sebelumnya (positif = naik, negatif = turun, 0 = stabil) |

**File Terkait:**
- `NewJournalRepository.kt` (line 120-143 untuk save emotion scores, line 207-227 untuk read all emotion scores)

---

## **Tabel Data Achievement (Badges) | TIDAK DISIMPAN DI FIRESTORE**

**CATATAN PENTING:** Badges/achievements **TIDAK DISIMPAN** sebagai collection di Firestore. Badges dihitung secara **real-time** setiap kali user membuka halaman Achievements berdasarkan data `currentDayStreak` dan jumlah dokumen di subcollection `journals`.

### **Aturan Badge Kategori Streak:**

| Badge Level | Syarat (Hari Berturut-turut) | Icon | Deskripsi |
|-------------|------------------------------|------|-----------|
| Level 1 | 3 hari | 🔥 | 3 days in a row |
| Level 2 | 7 hari | ✨ | 7 days in a row |
| Level 3 | 14 hari | 🏅 | 14 days in a row |
| Level 4 | 21 hari | 💫 | 21 days in a row |
| Level 5 | 30 hari | 🏆 | 30 days in a row |
| Level 6 | 60 hari | 👑 | 60 days in a row |

### **Aturan Badge Kategori Journal:**

| Badge Level | Syarat (Total Jurnal) | Icon | Deskripsi |
|-------------|-----------------------|------|-----------|
| Level 1 | 1 jurnal | 📝 | 1 journal |
| Level 2 | 20 jurnal | 📘 | 20 journals |
| Level 3 | 50 jurnal | 📗 | 50 journals |
| Level 4 | 100 jurnal | 📙 | 100 journals |
| Level 5 | 200 jurnal | 📔 | 200 journals |

**File Terkait:**
- `MilestonesRepository.kt` (line 24-41 untuk get all badges, line 44-72 untuk streak badges rules, line 75-103 untuk journal badges rules)

---

## **Tabel Evolusi Pet | TIDAK DISIMPAN DI FIRESTORE**

**CATATAN PENTING:** Timeline evolusi pet **TIDAK DISIMPAN** sebagai collection di Firestore. Ini adalah konfigurasi hardcoded di `StreakRepository.kt` yang digunakan untuk menghitung level pet berdasarkan `currentDayStreak`.

### **Aturan Evolusi Pet:**

| Pet Level | Nama Pet | Syarat (Hari Berturut-turut) | Keterangan |
|-----------|----------|------------------------------|------------|
| Level 1 | Capybara | 1 hari | Pet awal, muncul saat user pertama kali journaling |
| Level 2 | Capybara | 3 hari | Capybara tumbuh lebih besar |
| Level 3 | Bear | 7 hari | Evolusi menjadi Bear |
| Level 4 | Dragon | 14 hari | Evolusi final menjadi Dragon |

**File Terkait:**
- `StreakRepository.kt` (line 21-26 untuk evolution timeline configuration, line 130-137 untuk calculate pet level)

---

## 🔗 **RELASI ANTAR COLLECTION/DOCUMENT**

### **1. User ↔ Journals (One-to-Many)**
- **Tipe Relasi:** Hierarchical Parent-Child (SubCollection)
- **Deskripsi:** Satu user dapat memiliki banyak jurnal
- **Implementasi:** SubCollection `journals` di dalam dokumen `users/{uid}`
- **Cascade Delete:** ✅ Ya - Jika user dihapus, semua jurnal ikut terhapus otomatis
- **File Terkait:** `JournalRepository.kt`, `NewJournalRepository.kt`

### **2. Journal ↔ AI Analysis (One-to-One/Many)**
- **Tipe Relasi:** Hierarchical Parent-Child (SubCollection)
- **Deskripsi:** Satu jurnal dapat memiliki satu atau lebih hasil analisis AI (biasanya hanya 1)
- **Implementasi:** SubCollection `aiAnalysis` di dalam dokumen `journals/{journalId}`
- **Cascade Delete:** ✅ Ya - Jika jurnal dihapus, AI analysis ikut terhapus (lihat `JournalRepository.kt` line 125-133)
- **File Terkait:** `NewJournalRepository.kt`

### **3. Journal ↔ Emotion Scores (One-to-Many)**
- **Tipe Relasi:** Hierarchical Parent-Child (SubCollection)
- **Deskripsi:** Satu jurnal dapat memiliki banyak skor emosi (biasanya 5-7 emosi berbeda)
- **Implementasi:** SubCollection `emotionScores` di dalam dokumen `journals/{journalId}`
- **Cascade Delete:** ✅ Ya - Jika jurnal dihapus, emotion scores ikut terhapus (lihat `JournalRepository.kt` line 136-143)
- **File Terkait:** `NewJournalRepository.kt`

### **4. User ↔ Streak Data (One-to-One Embedded)**
- **Tipe Relasi:** Embedded Fields (bukan collection terpisah)
- **Deskripsi:** Data streak disimpan langsung di dokumen user sebagai field
- **Implementasi:** Fields `currentDayStreak`, `lastJournalDate`, `currentPetLevel`, `totalJournalDays`, `highestEvolutionLevelAchieved` di dokumen `users/{uid}`
- **Update Trigger:** Otomatis setiap kali user membuat jurnal baru
- **File Terkait:** `StreakRepository.kt`

### **5. User ↔ Badges (Computed - No Storage)**
- **Tipe Relasi:** Computed/Calculated (TIDAK disimpan di database)
- **Deskripsi:** Badge dihitung secara real-time berdasarkan `currentDayStreak` dan jumlah dokumen di `journals`
- **Implementasi:** Tidak ada storage, dihitung on-the-fly di `MilestonesRepository.kt`
- **File Terkait:** `MilestonesRepository.kt`

---

## 📂 **VISUALISASI HIERARKI DATABASE**

```
Firestore Root
│
└── users/                                      (Collection)
    └── {uid}/                                  (Document - User ID dari Firebase Auth)
        │
        ├── [Field] fullName: String
        ├── [Field] email: String
        ├── [Field] phone: String?
        ├── [Field] dob: String?
        ├── [Field] createdAt: Long
        ├── [Field] currentDayStreak: Int
        ├── [Field] lastJournalDate: String
        ├── [Field] currentPetLevel: Int
        ├── [Field] totalJournalDays: Int
        ├── [Field] highestEvolutionLevelAchieved: Int
        │
        └── journals/                           (SubCollection)
            └── {journalId}/                    (Document - Auto ID)
                │
                ├── [Field] content: String
                ├── [Field] createdAt: Timestamp
                ├── [Field] day: Int
                ├── [Field] month: String
                ├── [Field] mood: String
                ├── [Field] dateTimestamp: Long
                │
                ├── aiAnalysis/                 (SubCollection)
                │   └── {analysisId}/           (Document - Auto ID)
                │       ├── [Field] dominantEmotion: String
                │       ├── [Field] predictionSummary: String
                │       ├── [Field] quote: String
                │       ├── [Field] recommendation: String
                │       ├── [Field] rootCause: String
                │       └── [Field] createdAt: Timestamp
                │
                └── emotionScores/              (SubCollection)
                    └── {scoreId}/              (Document - Auto ID, multiple docs)
                        ├── [Field] emotionName: String
                        ├── [Field] score: Int
                        ├── [Field] colorHex: String
                        └── [Field] comparisonTrend: Int
```

---

## 🔄 **FLOW DATA SAAT USER MEMBUAT JURNAL BARU**

### **Step 1: User Menulis Jurnal**
- **Screen:** `AddNewJournalScreen.kt`
- **Input:** Content (teks jurnal) + Mood (emoji yang dipilih)
- **Model:** `NewJournal(content, dateTimestamp, mood)`

### **Step 2: Simpan Jurnal ke Firestore**
- **Repository:** `NewJournalRepository.kt` → `insertJournal()`
- **Path:** `users/{uid}/journals/{autoId}`
- **Fields Disimpan:** content, createdAt, day, month, mood, dateTimestamp
- **Return:** journalId (String)

### **Step 3: Update Streak Otomatis**
- **Trigger:** Dipanggil otomatis di `insertJournal()` line 63
- **Repository:** `StreakRepository.kt` → `updateStreakOnNewJournal()`
- **Logic:**
  - Jika jurnal pertama → streak = 1
  - Jika hari berturut-turut → streak + 1
  - Jika terlewat 1+ hari → streak reset ke 1
  - Jika hari yang sama → tidak update
- **Update Fields:** currentDayStreak, lastJournalDate, currentPetLevel, totalJournalDays, highestEvolutionLevelAchieved

### **Step 4: Navigasi ke Analisis AI**
- **Navigation:** `navGraph.kt` line 66-71
- **Screen:** `JournalAnalysisScreen.kt`
- **Parameter:** journalContent (URL encoded) + journalId

### **Step 5: AI Menganalisis Jurnal**
- **API:** Google Gemini AI (API Key di `build.gradle.kts` line 22)
- **Input:** journalContent + mood
- **Output:** AI Analysis + Emotion Scores

### **Step 6: Simpan Hasil AI ke Firestore**
- **Repository:** `NewJournalRepository.kt`
- **Function 1:** `saveAiAnalysis()` → Simpan ke `journals/{journalId}/aiAnalysis/`
- **Function 2:** `saveEmotionScores()` → Simpan multiple docs ke `journals/{journalId}/emotionScores/`

### **Step 7: Update Badges (Real-time Calculation)**
- **Repository:** `MilestonesRepository.kt` → `getAllBadges()`
- **Tidak Disimpan:** Badge dihitung ulang setiap kali halaman Achievements dibuka
- **Data Source:** currentDayStreak + jumlah dokumen di journals/

---

## 🔄 **FLOW DATA SAAT USER MENGHAPUS JURNAL**

### **Step 1: User Klik Delete di History Screen**
- **Screen:** `JournalHistoryScreen.kt`
- **Input:** journalId

### **Step 2: Cascade Delete di Firestore**
- **Repository:** `JournalRepository.kt` → `deleteJournalEntry()`
- **Path:** `users/{uid}/journals/{journalId}`
- **Cascade Logic:**
  1. Hapus semua dokumen di `journals/{journalId}/aiAnalysis/`
  2. Hapus semua dokumen di `journals/{journalId}/emotionScores/`
  3. Hapus parent document `journals/{journalId}`

### **Step 3: Streak TIDAK Otomatis Update**
- **Catatan:** Saat ini, menghapus jurnal **TIDAK** mengupdate streak
- **Reason:** Streak hanya update saat **menambah** jurnal baru, bukan saat hapus

---

## 📋 **QUERY PATTERNS YANG DIGUNAKAN**

### **1. Get All Journals (Sorted by Date Descending)**
```kotlin
// File: JournalRepository.kt (line 74-80)
firestore.collection("users")
    .document(uid)
    .collection("journals")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
```

### **2. Get Latest AI Analysis for a Journal**
```kotlin
// File: NewJournalRepository.kt (line 181-190)
firestore.collection("users")
    .document(uid)
    .collection("journals")
    .document(journalId)
    .collection("aiAnalysis")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .limit(1)
    .get()
```

### **3. Count Total Journals (for Badges Calculation)**
```kotlin
// File: MilestonesRepository.kt (line 34-35)
val journalsSnapshot = userRef.collection("journals").get().await()
val totalJournalEntries = journalsSnapshot.size()
```

### **4. Get User Streak Data**
```kotlin
// File: StreakRepository.kt (line 36)
val userDoc = firestore.collection("users").document(uid).get().await()
val currentDayStreak = (userDoc.getLong("currentDayStreak") ?: 0L).toInt()
```

---

## 📁 **LOKASI FILE REPOSITORY**

Semua file yang berhubungan dengan database Firestore terletak di:

```
d:\PAB\Jourie\app\src\main\java\com\example\jourie\data\
├── firebase\
│   └── FirebaseAuthRepository.kt          # Autentikasi & Profil User
├── repository\
│   ├── JournalRepository.kt               # Read & Delete Jurnal
│   ├── NewJournalRepository.kt            # Create Jurnal & AI Analysis
│   ├── StreakRepository.kt                # Manajemen Streak & Pet Level
│   ├── MilestonesRepository.kt            # Hitung Badges Real-time
│   ├── MainDashboardRepository.kt         # Data Dashboard (Dummy)
│   └── UserProfileRepository.kt           # Data Profil (Dummy)
└── model\
    ├── JournalEntry.kt                    # Model Jurnal untuk UI
    ├── NewJournal.kt                      # Model Input Jurnal Baru
    ├── StreakData.kt                      # Model Data Streak
    ├── EvolutionStage.kt                  # Model Tahapan Evolusi Pet
    ├── Badge.kt                           # Model Achievement Badge
    ├── UserProfile.kt                     # Model Profil User
    ├── EmotionSnapshot.kt                 # Model Snapshot Emosi
    └── WellnessRecommendation.kt          # Model Rekomendasi Wellness
```

---

## ✅ **KESIMPULAN**

**Struktur database Jourie menggunakan pendekatan Hierarchical (Nested Collections) dengan:**

1. **1 Root Collection** (`users`)
2. **1 SubCollection per User** (`journals`)
3. **2 SubCollections per Journal** (`aiAnalysis` & `emotionScores`)

**Total Depth:** 3 levels (users → journals → aiAnalysis/emotionScores)

**Kelebihan:**
- ✅ Data terisolasi per user (privacy & security)
- ✅ Cascade delete otomatis (data integrity)
- ✅ Query sederhana dan cepat
- ✅ Struktur intuitif dan mudah dipahami
- ✅ Badges dihitung real-time (tidak perlu update manual)

**Kekurangan:**
- ❌ Tidak ada pagination (bisa lambat jika user punya 1000+ jurnal)
- ❌ Tidak ada indexing untuk query kompleks
- ❌ Badges tidak persistent (tidak ada timestamp `unlockedAt`)
- ❌ Belum ada Firestore Security Rules

---

**Dibuat oleh:** Antigravity AI Assistant  
**Tanggal:** 2026-01-03  
**Project:** Jourie - Mental Health Journaling App  
**Berdasarkan:** Existing Code Implementation (Actual Running Code)
