# 📊 STRUKTUR DATABASE FIRESTORE - JOURIE APP

## 🗂️ Lokasi File Database Repository

Semua file yang berhubungan dengan database Firestore terletak di:

```
d:\PAB\Jourie\app\src\main\java\com\example\jourie\data\
├── firebase\
│   └── FirebaseAuthRepository.kt          # Autentikasi & Profil User
├── repository\
│   ├── JournalRepository.kt               # CRUD Jurnal
│   ├── NewJournalRepository.kt            # Menyimpan Jurnal Baru & AI Analysis
│   ├── StreakRepository.kt                # Manajemen Streak Harian
│   ├── MilestonesRepository.kt            # Achievements/Badges
│   ├── MainDashboardRepository.kt         # Data Dashboard (Dummy)
│   └── UserProfileRepository.kt           # Data Profil (Dummy)
└── model\
    ├── JournalEntry.kt                    # Model Jurnal untuk UI
    ├── NewJournal.kt                      # Model Input Jurnal Baru
    ├── StreakData.kt                      # Model Data Streak
    ├── EvolutionStage.kt                  # Model Tahapan Evolusi Pet
    ├── Badge.kt                           # Model Achievement Badge
    ├── UserProfile.kt                     # Model Profil User (Dummy)
    ├── EmotionSnapshot.kt                 # Model Snapshot Emosi
    └── WellnessRecommendation.kt          # Model Rekomendasi Wellness
```

---

## 🏗️ ARSITEKTUR DATABASE FIRESTORE

### **Root Collection: `users`**

Ini adalah koleksi utama yang menyimpan semua data user. Setiap user memiliki **1 dokumen** dengan ID = **UID dari Firebase Auth**.

```
Firestore Root
└── users/                                  (Collection)
    └── {uid}/                              (Document - User ID dari Firebase Auth)
        ├── [Field] fullName: String        # Nama lengkap user
        ├── [Field] email: String           # Email user
        ├── [Field] phone: String?          # Nomor telepon (opsional)
        ├── [Field] dob: String?            # Tanggal lahir (opsional)
        ├── [Field] createdAt: Long         # Timestamp registrasi
        ├── [Field] currentDayStreak: Int   # Streak hari berturut-turut saat ini
        ├── [Field] lastJournalDate: String # Tanggal jurnal terakhir (format: yyyy-MM-dd)
        ├── [Field] currentPetLevel: Int    # Level pet saat ini (1-4)
        ├── [Field] totalJournalDays: Int   # Total hari user pernah journaling
        ├── [Field] highestEvolutionLevelAchieved: Int  # Level evolusi tertinggi yang pernah dicapai
        │
        └── journals/                       (SubCollection - Koleksi Jurnal User)
            └── {journalId}/                (Document - Auto ID dari Firestore)
                ├── [Field] content: String         # Isi jurnal yang ditulis user
                ├── [Field] createdAt: Timestamp    # Server timestamp saat jurnal dibuat
                ├── [Field] day: Int                # Tanggal (1-31)
                ├── [Field] month: String           # Bulan singkatan (Jan, Feb, Mar, dst.)
                ├── [Field] mood: String            # Mood yang dipilih user (Happy, Sad, Calm, dll.)
                ├── [Field] dateTimestamp: Long     # Timestamp lokal sebagai backup
                │
                ├── aiAnalysis/                     (SubCollection - Hasil Analisis AI)
                │   └── {analysisId}/               (Document - Auto ID)
                │       ├── [Field] dominantEmotion: String      # Emosi dominan hasil AI
                │       ├── [Field] predictionSummary: String    # Ringkasan prediksi AI
                │       ├── [Field] quote: String                # Quote inspiratif dari AI
                │       ├── [Field] recommendation: String       # Rekomendasi wellness dari AI
                │       ├── [Field] rootCause: String            # Akar penyebab emosi (AI analysis)
                │       └── [Field] createdAt: Timestamp         # Waktu analisis dibuat
                │
                └── emotionScores/                  (SubCollection - Skor Emosi Detail)
                    └── {scoreId}/                  (Document - Auto ID, bisa multiple)
                        ├── [Field] emotionName: String      # Nama emosi (Joy, Sadness, Anger, dll.)
                        ├── [Field] score: Int               # Skor emosi (0-100)
                        ├── [Field] colorHex: String         # Warna hex untuk visualisasi
                        └── [Field] comparisonTrend: Int     # Tren perbandingan dengan jurnal sebelumnya
```

---

## 🔗 RELASI & HUBUNGAN ANTAR COLLECTION/DOCUMENT

### **1. Relasi User ↔ Journals (One-to-Many)**

- **Tipe**: Hierarchical (Parent-Child)
- **Deskripsi**: Satu user (`users/{uid}`) dapat memiliki **banyak jurnal** (`journals/`)
- **Implementasi**: SubCollection `journals` berada di dalam dokumen user
- **Cascade Delete**: Ya - Jika user dihapus, semua jurnal dan sub-koleksinya ikut terhapus
- **File Terkait**: 
  - `NewJournalRepository.kt` (line 35-66) → Menyimpan jurnal baru
  - `JournalRepository.kt` (line 71-106) → Membaca semua jurnal user

### **2. Relasi Journal ↔ AI Analysis (One-to-One/Many)**

- **Tipe**: Hierarchical (Parent-Child)
- **Deskripsi**: Satu jurnal dapat memiliki **satu atau lebih hasil analisis AI** (biasanya 1)
- **Implementasi**: SubCollection `aiAnalysis` di dalam dokumen journal
- **Cascade Delete**: Ya - Saat journal dihapus, aiAnalysis ikut terhapus (lihat `JournalRepository.kt` line 125-133)
- **File Terkait**:
  - `NewJournalRepository.kt` (line 90-114) → Menyimpan hasil AI analysis
  - `NewJournalRepository.kt` (line 178-201) → Membaca AI analysis terbaru

### **3. Relasi Journal ↔ Emotion Scores (One-to-Many)**

- **Tipe**: Hierarchical (Parent-Child)
- **Deskripsi**: Satu jurnal dapat memiliki **banyak skor emosi** (biasanya 5-7 emosi berbeda)
- **Implementasi**: SubCollection `emotionScores` di dalam dokumen journal
- **Cascade Delete**: Ya - Saat journal dihapus, emotionScores ikut terhapus (lihat `JournalRepository.kt` line 136-143)
- **File Terkait**:
  - `NewJournalRepository.kt` (line 120-143) → Menyimpan multiple emotion scores
  - `NewJournalRepository.kt` (line 207-227) → Membaca semua emotion scores

### **4. Relasi User ↔ Streak Data (One-to-One)**

- **Tipe**: Embedded Fields (bukan collection terpisah)
- **Deskripsi**: Data streak disimpan **langsung di dokumen user** sebagai field
- **Field Terkait**: 
  - `currentDayStreak` → Streak hari ini
  - `lastJournalDate` → Tanggal jurnal terakhir
  - `currentPetLevel` → Level pet berdasarkan streak
  - `totalJournalDays` → Total hari journaling
  - `highestEvolutionLevelAchieved` → Level tertinggi yang pernah dicapai
- **Update Trigger**: Setiap kali user membuat jurnal baru
- **File Terkait**:
  - `StreakRepository.kt` (line 32-61) → Membaca streak data
  - `StreakRepository.kt` (line 72-128) → Update streak saat jurnal baru dibuat
  - `NewJournalRepository.kt` (line 63) → Trigger update streak

### **5. Relasi User ↔ Badges/Achievements (Computed - No Storage)**

- **Tipe**: Computed/Calculated (TIDAK disimpan di database)
- **Deskripsi**: Badge **dihitung secara real-time** berdasarkan:
  - `currentDayStreak` dari dokumen user
  - Jumlah dokumen di subcollection `journals`
- **Tidak Ada Storage**: Badge tidak disimpan sebagai collection terpisah
- **File Terkait**:
  - `MilestonesRepository.kt` (line 24-41) → Menghitung badge secara real-time
  - `MilestonesRepository.kt` (line 44-72) → Aturan badge kategori Streak
  - `MilestonesRepository.kt` (line 75-103) → Aturan badge kategori Journal

---

## 📋 DETAIL FIELD PER COLLECTION

### **Collection: `users/{uid}` (Document Level)**

| Field Name | Type | Required | Default | Deskripsi |
|------------|------|----------|---------|-----------|
| `fullName` | String | ✅ Yes | - | Nama lengkap user |
| `email` | String | ✅ Yes | - | Email user (dari Firebase Auth) |
| `phone` | String | ❌ No | null | Nomor telepon user |
| `dob` | String | ❌ No | null | Tanggal lahir user |
| `createdAt` | Long | ✅ Yes | System.currentTimeMillis() | Timestamp registrasi |
| `currentDayStreak` | Int | ❌ No | 0 | Jumlah hari berturut-turut journaling |
| `lastJournalDate` | String | ❌ No | null | Tanggal jurnal terakhir (yyyy-MM-dd) |
| `currentPetLevel` | Int | ❌ No | 1 | Level pet saat ini (1-4) |
| `totalJournalDays` | Int | ❌ No | 0 | Total hari user pernah journaling |
| `highestEvolutionLevelAchieved` | Int | ❌ No | 1 | Level evolusi tertinggi yang pernah dicapai |

**File Terkait**: `FirebaseAuthRepository.kt` (line 14-30 untuk register, line 55-70 untuk read)

---

### **SubCollection: `users/{uid}/journals/{journalId}`**

| Field Name | Type | Required | Default | Deskripsi |
|------------|------|----------|---------|-----------|
| `content` | String | ✅ Yes | - | Isi jurnal yang ditulis user |
| `createdAt` | Timestamp | ✅ Yes | FieldValue.serverTimestamp() | Server timestamp saat jurnal dibuat |
| `day` | Int | ✅ Yes | - | Tanggal (1-31) |
| `month` | String | ✅ Yes | - | Bulan singkatan (Jan, Feb, Mar, dll.) |
| `mood` | String | ✅ Yes | "Neutral" | Mood yang dipilih user |
| `dateTimestamp` | Long | ✅ Yes | - | Timestamp lokal sebagai backup |

**File Terkait**: `NewJournalRepository.kt` (line 35-66 untuk insert)

---

### **SubCollection: `users/{uid}/journals/{journalId}/aiAnalysis/{analysisId}`**

| Field Name | Type | Required | Default | Deskripsi |
|------------|------|----------|---------|-----------|
| `dominantEmotion` | String | ✅ Yes | - | Emosi dominan hasil analisis AI |
| `predictionSummary` | String | ✅ Yes | - | Ringkasan prediksi AI |
| `quote` | String | ✅ Yes | - | Quote inspiratif dari AI |
| `recommendation` | String | ✅ Yes | - | Rekomendasi wellness dari AI |
| `rootCause` | String | ✅ Yes | - | Akar penyebab emosi (AI analysis) |
| `createdAt` | Timestamp | ✅ Yes | FieldValue.serverTimestamp() | Waktu analisis dibuat |

**File Terkait**: `NewJournalRepository.kt` (line 90-114 untuk save, line 178-201 untuk read)

---

### **SubCollection: `users/{uid}/journals/{journalId}/emotionScores/{scoreId}`**

| Field Name | Type | Required | Default | Deskripsi |
|------------|------|----------|---------|-----------|
| `emotionName` | String | ✅ Yes | - | Nama emosi (Joy, Sadness, Anger, dll.) |
| `score` | Int | ✅ Yes | - | Skor emosi (0-100) |
| `colorHex` | String | ✅ Yes | - | Warna hex untuk visualisasi (#RRGGBB) |
| `comparisonTrend` | Int | ✅ Yes | - | Tren perbandingan dengan jurnal sebelumnya |

**File Terkait**: `NewJournalRepository.kt` (line 120-143 untuk save, line 207-227 untuk read)

---

## 🔄 FLOW DATA SAAT USER MEMBUAT JURNAL BARU

Berikut adalah alur lengkap data dari UI hingga tersimpan di Firestore:

### **Step 1: User Menulis Jurnal**
- **Screen**: `AddNewJournalScreen.kt`
- **Input**: Content (teks jurnal) + Mood (emoji)
- **Model**: `NewJournal(content, dateTimestamp, mood)`

### **Step 2: Simpan Jurnal ke Firestore**
- **Repository**: `NewJournalRepository.kt` → `insertJournal()`
- **Path**: `users/{uid}/journals/{autoId}`
- **Fields Disimpan**: content, createdAt, day, month, mood, dateTimestamp
- **Return**: journalId (String)

### **Step 3: Update Streak Otomatis**
- **Trigger**: Dipanggil otomatis di `insertJournal()` line 63
- **Repository**: `StreakRepository.kt` → `updateStreakOnNewJournal()`
- **Logic**:
  - Jika jurnal pertama → streak = 1
  - Jika hari berturut-turut → streak + 1
  - Jika terlewat 1+ hari → streak reset ke 1
  - Jika hari yang sama → tidak update
- **Update Fields**: currentDayStreak, lastJournalDate, currentPetLevel, totalJournalDays, highestEvolutionLevelAchieved

### **Step 4: Navigasi ke Analisis AI**
- **Navigation**: `navGraph.kt` line 66-71
- **Screen**: `JournalAnalysisScreen.kt`
- **Parameter**: journalContent (encoded) + journalId

### **Step 5: AI Menganalisis Jurnal (Gemini API)**
- **API**: Google Gemini AI (API Key di `build.gradle.kts` line 22)
- **Input**: journalContent + mood
- **Output**: 
  - AI Analysis (dominantEmotion, predictionSummary, quote, recommendation, rootCause)
  - Emotion Scores (list of emotions dengan score masing-masing)

### **Step 6: Simpan Hasil AI ke Firestore**
- **Repository**: `NewJournalRepository.kt`
- **Function 1**: `saveAiAnalysis()` → Simpan ke `journals/{journalId}/aiAnalysis/`
- **Function 2**: `saveEmotionScores()` → Simpan multiple docs ke `journals/{journalId}/emotionScores/`

### **Step 7: Update Badges (Real-time Calculation)**
- **Repository**: `MilestonesRepository.kt` → `getAllBadges()`
- **Tidak Disimpan**: Badge dihitung ulang setiap kali dibuka
- **Data Source**:
  - Streak badges → dari `currentDayStreak`
  - Journal badges → dari jumlah dokumen di `journals/`

---

## 🔄 FLOW DATA SAAT USER MENGHAPUS JURNAL

### **Step 1: User Klik Delete di History Screen**
- **Screen**: `JournalHistoryScreen.kt`
- **Input**: journalId

### **Step 2: Cascade Delete di Firestore**
- **Repository**: `JournalRepository.kt` → `deleteJournalEntry()`
- **Path**: `users/{uid}/journals/{journalId}`
- **Cascade Logic**:
  1. Hapus semua dokumen di `journals/{journalId}/aiAnalysis/`
  2. Hapus semua dokumen di `journals/{journalId}/emotionScores/`
  3. Hapus parent document `journals/{journalId}`

### **Step 3: Streak TIDAK Otomatis Update**
- **Catatan**: Saat ini, menghapus jurnal **TIDAK** mengupdate streak
- **Reason**: Streak hanya update saat **menambah** jurnal baru, bukan saat hapus

---

## 📊 QUERY PATTERNS YANG DIGUNAKAN

### **1. Get All Journals (Sorted by Date)**
```kotlin
// File: JournalRepository.kt (line 74-80)
firestore.collection("users")
    .document(uid)
    .collection("journals")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
```

### **2. Get Latest AI Analysis**
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

### **3. Count Total Journals (for Badges)**
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

## 🎯 ATURAN BISNIS PENTING

### **Streak Rules (StreakRepository.kt)**

1. **Level Pet Evolution**:
   - Level 1: Capybara (1 hari)
   - Level 2: Capybara (3 hari)
   - Level 3: Bear (7 hari)
   - Level 4: Dragon (14 hari)

2. **Streak Calculation**:
   - Jurnal di hari yang sama → Streak tidak berubah
   - Jurnal hari berturut-turut → Streak + 1
   - Terlewat 1+ hari → Streak reset ke 1

### **Badge Rules (MilestonesRepository.kt)**

**Streak Badges**:
- Level 1: 3 hari berturut-turut 🔥
- Level 2: 7 hari berturut-turut ✨
- Level 3: 14 hari berturut-turut 🏅
- Level 4: 21 hari berturut-turut 💫
- Level 5: 30 hari berturut-turut 🏆
- Level 6: 60 hari berturut-turut 👑

**Journal Badges**:
- Level 1: 1 jurnal 📝
- Level 2: 20 jurnal 📘
- Level 3: 50 jurnal 📗
- Level 4: 100 jurnal 📙
- Level 5: 200 jurnal 📔

---

## 🔐 SECURITY CONSIDERATIONS

### **Current Implementation**:
- ✅ Semua operasi menggunakan `FirebaseAuth.currentUser?.uid`
- ✅ Data user terisolasi per UID
- ✅ Cascade delete untuk mencegah orphaned documents

### **Missing (Perlu Firestore Security Rules)**:
- ❌ Belum ada Firestore Security Rules di file `firestore.rules`
- ❌ User bisa akses data user lain jika tahu UID-nya
- ⚠️ **REKOMENDASI**: Tambahkan security rules:

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

---

## 📝 CATATAN TAMBAHAN

### **Data yang TIDAK Disimpan di Firestore**:

1. **Badges/Achievements** → Dihitung real-time dari streak & jumlah jurnal
2. **Pet Evolution Timeline** → Hardcoded di `StreakRepository.kt` line 21-26
3. **Dashboard Emotions** → Saat ini masih dummy data di `MainDashboardRepository.kt`
4. **User Profile (name, email, phone, dob)** → Disimpan di `UserProfileRepository.kt` (dummy) dan `FirebaseAuthRepository.kt` (real)

### **Potential Issues**:

1. **Duplicate Data**: 
   - Email disimpan di Firebase Auth DAN di Firestore `users/{uid}/email`
   - Jika user update email di Auth, perlu sync manual ke Firestore

2. **No Indexing**:
   - Query `orderBy("createdAt")` mungkin perlu composite index jika ada filter tambahan

3. **No Pagination**:
   - `getAllJournalEntries()` mengambil SEMUA jurnal sekaligus
   - Bisa lambat jika user punya 1000+ jurnal

---

## 🎨 VISUALISASI STRUKTUR DATABASE

```
Firestore
│
└── users (Collection)
    │
    └── {uid} (Document)
        │
        ├── fullName: "John Doe"
        ├── email: "john@example.com"
        ├── phone: "+62812345678"
        ├── dob: "1995-03-15"
        ├── createdAt: 1704326400000
        ├── currentDayStreak: 7
        ├── lastJournalDate: "2026-01-03"
        ├── currentPetLevel: 3
        ├── totalJournalDays: 15
        ├── highestEvolutionLevelAchieved: 3
        │
        └── journals (SubCollection)
            │
            ├── {journalId_1} (Document)
            │   ├── content: "Today was amazing..."
            │   ├── createdAt: Timestamp
            │   ├── day: 3
            │   ├── month: "Jan"
            │   ├── mood: "Happy"
            │   ├── dateTimestamp: 1704326400000
            │   │
            │   ├── aiAnalysis (SubCollection)
            │   │   └── {analysisId} (Document)
            │   │       ├── dominantEmotion: "Joy"
            │   │       ├── predictionSummary: "You seem very happy..."
            │   │       ├── quote: "Happiness is..."
            │   │       ├── recommendation: "Keep doing..."
            │   │       ├── rootCause: "Positive social interaction"
            │   │       └── createdAt: Timestamp
            │   │
            │   └── emotionScores (SubCollection)
            │       ├── {scoreId_1} (Document)
            │       │   ├── emotionName: "Joy"
            │       │   ├── score: 85
            │       │   ├── colorHex: "#FFD700"
            │       │   └── comparisonTrend: 10
            │       │
            │       ├── {scoreId_2} (Document)
            │       │   ├── emotionName: "Sadness"
            │       │   ├── score: 15
            │       │   ├── colorHex: "#4169E1"
            │       │   └── comparisonTrend: -5
            │       │
            │       └── ... (more emotion scores)
            │
            ├── {journalId_2} (Document)
            │   └── ... (same structure)
            │
            └── ... (more journals)
```

---

## ✅ KESIMPULAN

**Struktur database Jourie menggunakan pendekatan Hierarchical (Nested Collections) dengan:**

1. **1 Root Collection** (`users`)
2. **1 SubCollection per User** (`journals`)
3. **2 SubCollections per Journal** (`aiAnalysis` & `emotionScores`)

**Total Depth**: 3 levels (users → journals → aiAnalysis/emotionScores)

**Kelebihan**:
- ✅ Data terisolasi per user
- ✅ Cascade delete otomatis
- ✅ Query sederhana dan cepat
- ✅ Struktur intuitif dan mudah dipahami

**Kekurangan**:
- ❌ Tidak ada pagination
- ❌ Tidak ada indexing untuk query kompleks
- ❌ Badges tidak persistent (dihitung ulang terus)
- ❌ Belum ada Firestore Security Rules

---

**Dibuat oleh**: Antigravity AI Assistant  
**Tanggal**: 2026-01-03  
**Project**: Jourie - Mental Health Journaling App
