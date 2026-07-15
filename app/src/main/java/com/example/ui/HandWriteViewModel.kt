package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.AnalysisResult
import com.example.data.api.GeminiAnalyzer
import com.example.data.database.AppDatabase
import com.example.data.model.CustomWorksheet
import com.example.data.model.PracticeSession
import com.example.data.model.StudentProfile
import com.example.data.model.UserProfile
import com.example.data.repository.HandWriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    Splash,
    Onboarding,
    Dashboard,
    Studio,
    Calligraphy,
    Worksheet,
    Analyzer,
    Games,
    Progress,
    Parent,
    Settings
}

class HandWriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HandWriteRepository

    // State flows from database
    val userProfile: StateFlow<UserProfile?>
    val allSessions: StateFlow<List<PracticeSession>>
    val allWorksheets: StateFlow<List<CustomWorksheet>>
    val allStudents: StateFlow<List<StudentProfile>>

    // UI States
    var currentScreen by mutableStateOf(AppScreen.Splash)
    var setupLanguage by mutableStateOf("English")
    var setupAgeGroup by mutableStateOf("Adult")
    var setupSkillLevel by mutableStateOf("Beginner")

    // Studio Tracing Settings
    var selectedLanguage by mutableStateOf("English")
    var selectedCategory by mutableStateOf("Alphabets") // Vowels, Consonants, Numbers, Words, Sentences, Paragraphs
    var currentCharacter by mutableStateOf("A")
    var brushColorHex by mutableStateOf("#4F46E5")
    var brushSize by mutableStateOf(12f)
    var activeBrushType by mutableStateOf("Fountain Pen") // Fountain Pen, Brush, Neon, Pencil, Marker, Neon Glow
    var practiceNotebookStyle by mutableStateOf("School Rule") // School Rule, College Rule, Grid, Blank, Dot Grid

    // Handwriting Analyzer States
    var analyzerBitmap by mutableStateOf<Bitmap?>(null)
    var analyzerText by mutableStateOf("Quick brown fox")
    var isAnalyzing by mutableStateOf(false)
    var analysisResult by mutableStateOf<AnalysisResult?>(null)

    // Calligraphy Signature States
    var calligraphyName by mutableStateOf("Prince AR Abdur Rahman")
    var selectedCalligraphyStyle by mutableStateOf("Classic Golden Grid") // Neon Magic, Minimal Ink, Royal Gold, Floral Swirl
    var signatureHistory = mutableStateListOf<String>()

    // Custom Font Creator States
    var drawnFontCharacters = mutableStateOf<Map<Char, List<List<androidx.compose.ui.geometry.Offset>>>>(emptyMap())
    var fontCreatorCurrentChar by mutableStateOf('A')
    var customFontGeneratedName by mutableStateOf("")

    // Games state
    var gameScore by mutableStateOf(0)
    var gameXpEarned by mutableStateOf(0)
    var selectedMiniGame by mutableStateOf("Letter Race") // Letter Race, Letter Puzzle, Alphabet Memory, Word Connect, Missing Letter, Match Letter, Speed Tap, Letter Hunt, Number Puzzle

    // --- Smart Practice Engine & Customizations ---
    var difficultyLevel by mutableStateOf("Medium") // Easy, Medium, Hard
    var incorrectLettersSet by mutableStateOf(setOf("A", "ক", "あ"))
    var activePlanName by mutableStateOf<String?>(null) // "Daily Plan", "Weekly Plan", "365 Days Challenge", or null
    var activePlanIndex by mutableStateOf(0)
    var activePlanChars by mutableStateOf(listOf<String>())

    // Customize Colors
    var selectedPenType by mutableStateOf("Fountain Pen") // Pencil, HB Pencil, 2B Pencil, Ball Pen, Gel Pen, Fountain Pen, Brush Pen, Marker, Chalk, Crayon, Calligraphy Pen, Highlighter
    var inkColorHex by mutableStateOf("#4F46E5")
    var paperColorHex by mutableStateOf("#F8FAFC")
    var customThemeName by mutableStateOf("Classic Theme") // Classic, Vintage, Cute, Dark Mode

    // Planner Goals
    var dailyGoalMinutes by mutableStateOf(15)
    var weeklyGoalMinutes by mutableStateOf(90)
    var monthlyGoalMinutes by mutableStateOf(360)
    var remindersEnabled by mutableStateOf(true)

    // Family Profiles (Family Mode)
    var currentProfileIndex by mutableStateOf(0)
    val familyProfiles = listOf(
        "Prince AR (Adult)",
        "Abdur Rahman (Kid)",
        "NexVora Guest"
    )

    // Sticker Shop unlocked items
    var unlockedNotebookCovers = mutableStateListOf("Default Canvas", "Galaxy Cover")
    var unlockedPens = mutableStateListOf("Fountain Pen", "Pencil", "Brush Pen")
    var unlockedAvatars = mutableStateListOf("Cat Avatar", "Astronaut")

    // Sound and vibration toggles
    var soundEffectsEnabled by mutableStateOf(true)
    var hapticFeedbackEnabled by mutableStateOf(true)

    init {
        val database = AppDatabase.getDatabase(application)
        repository = HandWriteRepository(database.handWriteDao())

        userProfile = repository.userProfile.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

        allSessions = repository.allSessions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allWorksheets = repository.allWorksheets.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allStudents = repository.allStudents.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Determine initial screen based on user profile existence
        viewModelScope.launch {
            repository.userProfile.collect { profile ->
                if (currentScreen == AppScreen.Splash) {
                    if (profile == null) {
                        currentScreen = AppScreen.Onboarding
                    } else {
                        // Profile exists, load settings
                        setupLanguage = profile.selectedLanguage
                        setupAgeGroup = profile.ageGroup
                        setupSkillLevel = profile.skillLevel
                        selectedLanguage = profile.selectedLanguage
                        currentScreen = AppScreen.Dashboard
                    }
                }
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            val profile = UserProfile(
                selectedLanguage = setupLanguage,
                ageGroup = setupAgeGroup,
                skillLevel = setupSkillLevel,
                xp = 50, // Welcome gift
                coins = 150,
                diamonds = 15,
                streak = 1,
                level = 1,
                lastPracticeTime = System.currentTimeMillis()
            )
            repository.saveUserProfile(profile)
            selectedLanguage = setupLanguage
            currentScreen = AppScreen.Dashboard
        }
    }

    fun earnRewards(xp: Int, coins: Int, diamonds: Int) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile()
            val newXp = current.xp + xp
            val levelIncrement = if (newXp >= current.level * 100) 1 else 0
            val updatedProfile = current.copy(
                xp = newXp % (current.level * 100),
                level = current.level + levelIncrement,
                coins = current.coins + coins,
                diamonds = current.diamonds + diamonds,
                streak = if (System.currentTimeMillis() - current.lastPracticeTime < 86400000 * 2) current.streak + 1 else 1,
                lastPracticeTime = System.currentTimeMillis()
            )
            repository.saveUserProfile(updatedProfile)
        }
    }

    fun savePracticeSession(character: String, accuracy: Float, speedMs: Long, score: Int) {
        viewModelScope.launch {
            val session = PracticeSession(
                character = character,
                language = selectedLanguage,
                category = selectedCategory,
                accuracy = accuracy,
                speedMs = speedMs,
                score = score
            )
            repository.addPracticeSession(session)
            // Earn some rewards based on accuracy
            val xpEarned = (accuracy * 20).toInt() + 5
            val coinsEarned = (accuracy * 10).toInt() + 2
            earnRewards(xpEarned, coinsEarned, if (score > 90) 1 else 0)
        }
    }

    fun resetAllSessions() {
        viewModelScope.launch {
            repository.clearSessions()
        }
    }

    fun generateWorksheet(title: String, content: String, style: String) {
        viewModelScope.launch {
            val worksheet = CustomWorksheet(
                title = title,
                content = content,
                pageStyle = style
            )
            repository.addWorksheet(worksheet)
            earnRewards(15, 10, 0)
        }
    }

    fun deleteWorksheet(id: Long) {
        viewModelScope.launch {
            repository.deleteWorksheet(id)
        }
    }

    fun addStudent(name: String, age: Int, classGroup: String, goal: String) {
        viewModelScope.launch {
            val student = StudentProfile(
                name = name,
                age = age,
                classGroup = classGroup,
                goal = goal
            )
            repository.addStudent(student)
        }
    }

    fun deleteStudent(id: Long) {
        viewModelScope.launch {
            repository.deleteStudent(id)
        }
    }

    fun analyzePhoto(bitmap: Bitmap?, language: String, userText: String) {
        viewModelScope.launch {
            isAnalyzing = true
            val result = GeminiAnalyzer.analyzeWriting(bitmap, language, userText)
            analysisResult = result
            isAnalyzing = false
            earnRewards(25, 15, 1) // Rewarded for AI evaluation!
        }
    }

    fun activatePremium() {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile()
            repository.saveUserProfile(current.copy(isPremium = true))
        }
    }
}
