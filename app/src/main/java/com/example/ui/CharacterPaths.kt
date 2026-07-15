package com.example.ui

import androidx.compose.ui.geometry.Offset

data class CharacterTemplate(
    val char: String,
    val language: String,
    val category: String,
    val dots: List<Offset>, // Path template dots in 0.0 to 1.0 coordinate system
    val strokePaths: List<List<Offset>> // Separate strokes for animation
)

object CharacterPaths {
    val templates = listOf(
        // English Capital A
        CharacterTemplate(
            char = "A",
            language = "English",
            category = "Alphabets",
            dots = listOf(
                Offset(0.5f, 0.15f), Offset(0.45f, 0.25f), Offset(0.4f, 0.35f), Offset(0.35f, 0.45f), Offset(0.3f, 0.55f), Offset(0.25f, 0.65f), Offset(0.2f, 0.75f), // stroke 1 left line
                Offset(0.5f, 0.15f), Offset(0.55f, 0.25f), Offset(0.6f, 0.35f), Offset(0.65f, 0.45f), Offset(0.7f, 0.55f), Offset(0.75f, 0.65f), Offset(0.8f, 0.75f), // stroke 2 right line
                Offset(0.35f, 0.5f), Offset(0.45f, 0.5f), Offset(0.55f, 0.5f), Offset(0.65f, 0.5f) // stroke 3 crossbar
            ),
            strokePaths = listOf(
                listOf(Offset(0.5f, 0.15f), Offset(0.2f, 0.75f)),
                listOf(Offset(0.5f, 0.15f), Offset(0.8f, 0.75f)),
                listOf(Offset(0.35f, 0.5f), Offset(0.65f, 0.5f))
            )
        ),
        // English B
        CharacterTemplate(
            char = "B",
            language = "English",
            category = "Alphabets",
            dots = listOf(
                Offset(0.3f, 0.15f), Offset(0.3f, 0.25f), Offset(0.3f, 0.35f), Offset(0.3f, 0.45f), Offset(0.3f, 0.55f), Offset(0.3f, 0.65f), Offset(0.3f, 0.75f),
                Offset(0.3f, 0.15f), Offset(0.45f, 0.15f), Offset(0.55f, 0.2f), Offset(0.55f, 0.3f), Offset(0.45f, 0.4f), Offset(0.3f, 0.4f),
                Offset(0.3f, 0.4f), Offset(0.48f, 0.4f), Offset(0.58f, 0.45f), Offset(0.58f, 0.6f), Offset(0.48f, 0.75f), Offset(0.3f, 0.75f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.3f, 0.15f), Offset(0.3f, 0.75f)),
                listOf(Offset(0.3f, 0.15f), Offset(0.55f, 0.2f), Offset(0.3f, 0.4f)),
                listOf(Offset(0.3f, 0.4f), Offset(0.58f, 0.5f), Offset(0.3f, 0.75f))
            )
        ),
        // Bengali 'অ' (Vowel)
        CharacterTemplate(
            char = "অ",
            language = "Bengali",
            category = "Swarobarna",
            dots = listOf(
                // Matra (Top bar)
                Offset(0.2f, 0.2f), Offset(0.3f, 0.2f), Offset(0.4f, 0.2f), Offset(0.5f, 0.2f), Offset(0.6f, 0.2f), Offset(0.7f, 0.2f), Offset(0.8f, 0.2f),
                // Inner circle and loop
                Offset(0.35f, 0.35f), Offset(0.3f, 0.4f), Offset(0.35f, 0.45f), Offset(0.45f, 0.4f), Offset(0.45f, 0.33f), Offset(0.35f, 0.35f),
                // Bottom curve going up
                Offset(0.45f, 0.4f), Offset(0.5f, 0.5f), Offset(0.45f, 0.62f), Offset(0.35f, 0.62f), Offset(0.3f, 0.55f),
                // Wave and tail
                Offset(0.3f, 0.55f), Offset(0.4f, 0.72f), Offset(0.55f, 0.72f), Offset(0.65f, 0.65f), Offset(0.65f, 0.5f),
                // Vertical bar
                Offset(0.65f, 0.22f), Offset(0.65f, 0.35f), Offset(0.65f, 0.55f), Offset(0.65f, 0.72f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.2f, 0.2f), Offset(0.8f, 0.2f)), // Matra
                listOf(Offset(0.35f, 0.35f), Offset(0.3f, 0.4f), Offset(0.45f, 0.4f), Offset(0.35f, 0.35f)), // Circle
                listOf(Offset(0.45f, 0.4f), Offset(0.45f, 0.62f), Offset(0.3f, 0.55f), Offset(0.65f, 0.72f)), // Body
                listOf(Offset(0.65f, 0.22f), Offset(0.65f, 0.72f)) // Bar
            )
        ),
        // Bengali 'ক' (Consonant)
        CharacterTemplate(
            char = "ক",
            language = "Bengali",
            category = "Banjonbarna",
            dots = listOf(
                // Matra
                Offset(0.2f, 0.2f), Offset(0.3f, 0.2f), Offset(0.4f, 0.2f), Offset(0.5f, 0.2f), Offset(0.6f, 0.2f), Offset(0.7f, 0.2f), Offset(0.8f, 0.2f),
                // Slanted down line
                Offset(0.45f, 0.2f), Offset(0.35f, 0.45f), Offset(0.3f, 0.65f), Offset(0.45f, 0.72f),
                // Vertical stem
                Offset(0.65f, 0.2f), Offset(0.65f, 0.45f), Offset(0.65f, 0.72f),
                // Outer loop
                Offset(0.45f, 0.72f), Offset(0.55f, 0.65f), Offset(0.65f, 0.55f),
                // Left wings
                Offset(0.45f, 0.2f), Offset(0.3f, 0.35f), Offset(0.25f, 0.48f), Offset(0.35f, 0.52f), Offset(0.45f, 0.45f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.2f, 0.2f), Offset(0.8f, 0.2f)),
                listOf(Offset(0.65f, 0.2f), Offset(0.65f, 0.72f)),
                listOf(Offset(0.45f, 0.2f), Offset(0.3f, 0.45f), Offset(0.45f, 0.72f)),
                listOf(Offset(0.45f, 0.72f), Offset(0.65f, 0.55f)),
                listOf(Offset(0.45f, 0.2f), Offset(0.25f, 0.4f), Offset(0.45f, 0.45f))
            )
        ),
        // Arabic Alif
        CharacterTemplate(
            char = "أ",
            language = "Arabic",
            category = "Alphabets",
            dots = listOf(
                Offset(0.5f, 0.15f), Offset(0.5f, 0.25f), Offset(0.5f, 0.35f), Offset(0.5f, 0.45f), Offset(0.5f, 0.55f), Offset(0.5f, 0.65f), Offset(0.5f, 0.75f),
                // Hamza (Top curve)
                Offset(0.45f, 0.1f), Offset(0.52f, 0.08f), Offset(0.55f, 0.12f), Offset(0.48f, 0.15f), Offset(0.42f, 0.15f), Offset(0.58f, 0.15f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.5f, 0.15f), Offset(0.5f, 0.75f)),
                listOf(Offset(0.55f, 0.12f), Offset(0.42f, 0.15f), Offset(0.58f, 0.15f))
            )
        ),
        // Hindi 'अ'
        CharacterTemplate(
            char = "अ",
            language = "Hindi",
            category = "Alphabets",
            dots = listOf(
                // Top curve
                Offset(0.3f, 0.25f), Offset(0.45f, 0.22f), Offset(0.5f, 0.32f), Offset(0.4f, 0.42f), Offset(0.3f, 0.4f),
                // Bottom curve
                Offset(0.4f, 0.42f), Offset(0.52f, 0.48f), Offset(0.5f, 0.62f), Offset(0.35f, 0.68f), Offset(0.28f, 0.62f),
                // Connection bar
                Offset(0.42f, 0.45f), Offset(0.52f, 0.45f), Offset(0.65f, 0.45f),
                // Vertical line
                Offset(0.65f, 0.25f), Offset(0.65f, 0.45f), Offset(0.65f, 0.68f),
                // Shirorekha (Top bar)
                Offset(0.25f, 0.2f), Offset(0.4f, 0.2f), Offset(0.55f, 0.2f), Offset(0.72f, 0.2f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.3f, 0.25f), Offset(0.5f, 0.32f), Offset(0.4f, 0.42f)),
                listOf(Offset(0.4f, 0.42f), Offset(0.5f, 0.55f), Offset(0.35f, 0.68f)),
                listOf(Offset(0.42f, 0.45f), Offset(0.65f, 0.45f)),
                listOf(Offset(0.65f, 0.25f), Offset(0.65f, 0.68f)),
                listOf(Offset(0.25f, 0.2f), Offset(0.72f, 0.2f))
            )
        ),
        // Japanese Hiragana あ (A)
        CharacterTemplate(
            char = "あ",
            language = "Japanese",
            category = "Hiragana",
            dots = listOf(
                // Stroke 1: horizontal bar
                Offset(0.3f, 0.35f), Offset(0.45f, 0.35f), Offset(0.65f, 0.35f),
                // Stroke 2: vertical curved bar
                Offset(0.48f, 0.18f), Offset(0.48f, 0.4f), Offset(0.45f, 0.65f), Offset(0.4f, 0.78f),
                // Stroke 3: central loop
                Offset(0.38f, 0.5f), Offset(0.58f, 0.42f), Offset(0.68f, 0.55f), Offset(0.58f, 0.72f), Offset(0.4f, 0.7f), Offset(0.35f, 0.58f), Offset(0.45f, 0.5f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.3f, 0.35f), Offset(0.65f, 0.35f)),
                listOf(Offset(0.48f, 0.18f), Offset(0.4f, 0.78f)),
                listOf(Offset(0.38f, 0.5f), Offset(0.68f, 0.55f), Offset(0.4f, 0.7f), Offset(0.45f, 0.5f))
            )
        ),
        // Chinese Basic Characters: 一 (One)
        CharacterTemplate(
            char = "一",
            language = "Chinese",
            category = "Hanzi",
            dots = listOf(
                Offset(0.2f, 0.5f), Offset(0.35f, 0.5f), Offset(0.5f, 0.5f), Offset(0.65f, 0.5f), Offset(0.8f, 0.5f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.2f, 0.5f), Offset(0.8f, 0.5f))
            )
        ),
        // Chinese Basic Characters: 人 (People)
        CharacterTemplate(
            char = "人",
            language = "Chinese",
            category = "Hanzi",
            dots = listOf(
                // Stroke 1: Left sweep
                Offset(0.5f, 0.2f), Offset(0.45f, 0.35f), Offset(0.38f, 0.52f), Offset(0.28f, 0.72f), Offset(0.18f, 0.82f),
                // Stroke 2: Right sweep
                Offset(0.45f, 0.45f), Offset(0.55f, 0.58f), Offset(0.68f, 0.72f), Offset(0.82f, 0.82f)
            ),
            strokePaths = listOf(
                listOf(Offset(0.5f, 0.2f), Offset(0.18f, 0.82f)),
                listOf(Offset(0.45f, 0.45f), Offset(0.82f, 0.82f))
            )
        )
    )

    fun getTemplateFor(char: String, language: String): CharacterTemplate {
        return templates.firstOrNull { it.char == char && it.language == language }
            ?: CharacterTemplate(
                char = char,
                language = language,
                category = "General",
                dots = listOf(
                    Offset(0.2f, 0.2f), Offset(0.8f, 0.2f),
                    Offset(0.2f, 0.8f), Offset(0.8f, 0.8f),
                    Offset(0.5f, 0.2f), Offset(0.5f, 0.8f)
                ),
                strokePaths = listOf(
                    listOf(Offset(0.2f, 0.2f), Offset(0.8f, 0.2f)),
                    listOf(Offset(0.2f, 0.8f), Offset(0.8f, 0.8f)),
                    listOf(Offset(0.5f, 0.2f), Offset(0.5f, 0.8f))
                )
            )
    }

    fun getAvailableCharacters(language: String): List<String> {
        return when (language) {
            "Bengali" -> listOf("অ", "আ", "ক", "খ", "গ", "ঘ", "ঙ", "১", "২", "৩", "মা", "বাবা", "বাংলাদেশ")
            "English" -> listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "a", "b", "c", "d", "e", "1", "2", "3", "School", "Apple", "Teacher")
            "Arabic" -> listOf("أ", "ب", "ت", "ث", "ج", "١", "٢", "٣", "سلام", "كتاب")
            "Hindi" -> listOf("अ", "क", "ख", "ग", "१", "२", "३", "नमस्ते", "भारत")
            "Japanese" -> listOf("あ", "い", "う", "え", "お", "ア", "イ", "ウ", "エ", "オ", "1", "2", "3", "サクラ", "富士山")
            "Chinese" -> listOf("一", "人", "大", "中", "天", "一", "二", "三", "你好", "老師")
            else -> listOf("A", "B", "C")
        }
    }
}
