package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val selectedLanguage: String = "English",
    val ageGroup: String = "Adult",
    val skillLevel: String = "Beginner",
    val xp: Int = 0,
    val coins: Int = 100,
    val diamonds: Int = 10,
    val streak: Int = 0,
    val level: Int = 1,
    val isPremium: Boolean = false,
    val lastPracticeTime: Long = 0L
)

@Entity(tableName = "practice_session")
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val character: String,
    val language: String,
    val category: String, // Vowels, Consonants, Words, Calligraphy, Signature
    val accuracy: Float,
    val speedMs: Long,
    val score: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_worksheet")
data class CustomWorksheet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val pageStyle: String, // School Rule, College Rule, Grid, Blank, Dot Grid, etc.
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "student_profile")
data class StudentProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val classGroup: String,
    val goal: String,
    val practiceMinutesToday: Int = 0,
    val averageAccuracy: Float = 0f,
    val weakLetters: String = "",
    val strongLetters: String = ""
)
