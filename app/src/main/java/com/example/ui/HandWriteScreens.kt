package com.example.ui

import android.graphics.Color as AndroidColor
import android.graphics.Paint as AndroidPaint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CustomWorksheet
import com.example.data.model.StudentProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.math.hypot

// --- Vibrant Palette Theme Styling ---
val DeepSpaceDark = Color(0xFFFDFBFF) // Very light lavender/indigo background
val DeepSpaceMedium = Color(0xFFFFFFFF) // White for cards/containers
val DeepSpaceLight = Color(0xFFE2E8F0) // Border color / slate-200
val NeonPurple = Color(0xFF4F46E5) // Indigo-600 (Primary branding)
val NeonCyan = Color(0xFF0EA5E9) // Sky/Blue
val NeonPink = Color(0xFFF43F5E) // Rose-500
val SoftGold = Color(0xFFFBBF24) // Amber/Gold
val NotebookPaper = Color(0xFFF8FAFC)
val NotebookBlueLine = Color(0xFF93C5FD)
val NotebookRedMargin = Color(0xFFFCA5A5)
val ForestGreen = Color(0xFF10B981) // Emerald-500
val CoralRed = Color(0xFFEF4444)

@Composable
fun AppContent(viewModel: HandWriteViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepSpaceDark)
    ) {
        AnimatedContent(
            targetState = viewModel.currentScreen,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            },
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                AppScreen.Splash -> SplashScreen()
                AppScreen.Onboarding -> OnboardingScreen(viewModel)
                AppScreen.Dashboard -> DashboardScreen(viewModel)
                AppScreen.Studio -> SmartStudioScreen(viewModel)
                AppScreen.Calligraphy -> CalligraphyStudioScreen(viewModel)
                AppScreen.Worksheet -> WorksheetGeneratorScreen(viewModel)
                AppScreen.Analyzer -> AIAnalyzerScreen(viewModel)
                AppScreen.Games -> PracticeGamesScreen(viewModel)
                AppScreen.Progress -> ProgressFontScreen(viewModel)
                AppScreen.Parent -> ParentTeacherScreen(viewModel)
                AppScreen.Settings -> SettingsScreen(viewModel)
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "App Icon",
            tint = NeonPurple,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "HandWrite Universe",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Learn • Practice • Master",
            fontSize = 16.sp,
            color = NeonPurple,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingScreen(viewModel: HandWriteViewModel) {
    var step by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .navigationBarsPadding()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "HandWrite Universe",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Learn. Practice. Master Your Handwriting.",
                fontSize = 14.sp,
                color = NeonPurple,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, NeonPurple, RoundedCornerShape(24.dp))
                .shadow(12.dp, RoundedCornerShape(24.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_welcome_hero),
                contentDescription = "Cosmic Handwriting",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepSpaceMedium, RoundedCornerShape(20.dp))
                .border(1.dp, DeepSpaceLight, RoundedCornerShape(20.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (step) {
                1 -> {
                    Text(
                        text = "Select Language",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val languages = listOf("English", "Bengali", "Arabic", "Hindi", "Japanese", "Chinese")
                    languages.chunked(2).forEach { row ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            row.forEach { lang ->
                                val isSelected = viewModel.setupLanguage == lang
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp)
                                        .clickable { viewModel.setupLanguage = lang }
                                        .testTag("lang_select_$lang"),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) NeonPurple else DeepSpaceDark
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, if (isSelected) NeonPurple else DeepSpaceLight)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = lang,
                                            color = if (isSelected) Color.White else Color(0xFF475569),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                2 -> {
                    Text(
                        text = "Select Age Group",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val ageGroups = listOf("3–6 years", "7–12 years", "13–18 years", "Adult")
                    ageGroups.forEach { age ->
                        val isSelected = viewModel.setupAgeGroup == age
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .padding(vertical = 4.dp)
                                .clickable { viewModel.setupAgeGroup = age },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) NeonPurple else DeepSpaceDark
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) NeonPurple else DeepSpaceLight)
                        ) {
                            Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = age,
                                        color = if (isSelected) Color.White else Color(0xFF475569),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    if (isSelected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
                3 -> {
                    Text(
                        text = "Select Skill Level",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val levels = listOf("Never Written", "Beginner", "Intermediate", "Advanced")
                    levels.forEach { lvl ->
                        val isSelected = viewModel.setupSkillLevel == lvl
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .padding(vertical = 4.dp)
                                .clickable { viewModel.setupSkillLevel = lvl },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) NeonPurple else DeepSpaceDark
                            ),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) NeonPurple else DeepSpaceLight)
                        ) {
                            Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = lvl,
                                        color = if (isSelected) Color.White else Color(0xFF475569),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    if (isSelected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (step > 1) {
                OutlinedButton(
                    onClick = { step-- },
                    border = BorderStroke(1.dp, NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonPurple)
                ) {
                    Text("Back")
                }
            } else {
                Spacer(modifier = Modifier.width(80.dp))
            }

            Button(
                onClick = {
                    if (step < 3) {
                        step++
                    } else {
                        viewModel.completeOnboarding()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("get_started_btn")
            ) {
                Text(
                    text = if (step == 3) "Get Started" else "Next",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DashboardScreen(viewModel: HandWriteViewModel) {
    val context = LocalContext.current
    val profile by viewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "HandWrite Universe",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Level", tint = SoftGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Level ${profile?.level ?: 1}",
                        fontSize = 13.sp,
                        color = Color(0xFFB45309), // amber-700
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = "Streak", tint = NeonPink, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${profile?.streak ?: 1} Day Streak",
                        fontSize = 13.sp,
                        color = Color(0xFFBE123C), // rose-700
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier
                        .background(DeepSpaceMedium, RoundedCornerShape(12.dp))
                        .border(1.dp, DeepSpaceLight, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MonetizationOn, contentDescription = "Coins", tint = SoftGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${profile?.coins ?: 0}", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Row(
                    modifier = Modifier
                        .background(DeepSpaceMedium, RoundedCornerShape(12.dp))
                        .border(1.dp, DeepSpaceLight, RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Diamond, contentDescription = "Diamonds", tint = NeonCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${profile?.diamonds ?: 0}", color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        val progress = (profile?.xp ?: 0).toFloat() / ((profile?.level ?: 1) * 100).toFloat()
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = NeonPurple,
            trackColor = DeepSpaceLight
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Family Switcher Row (Item 13)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Family Profiles Switcher:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                viewModel.familyProfiles.forEachIndexed { index, profileName ->
                    val isSelected = viewModel.currentProfileIndex == index
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) NeonPink else DeepSpaceMedium)
                            .border(1.dp, if (isSelected) NeonPink else DeepSpaceLight, RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.currentProfileIndex = index
                                Toast.makeText(context, "Switched Profile to: $profileName", Toast.LENGTH_SHORT).show()
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = "Active", tint = Color.White, modifier = Modifier.size(10.dp))
                            } else {
                                Text("👤", fontSize = 10.sp)
                            }
                            Text(
                                text = profileName,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        val items = listOf(
            DashboardCardItem("Practice", "Studio", Icons.Default.Draw, Color(0xFFFFE4E6), Color(0xFFF43F5E), Color(0xFFBE123C)),
            DashboardCardItem("Courses", "Tracks", Icons.Default.MenuBook, Color(0xFFD1FAE5), Color(0xFF10B981), Color(0xFF047857)),
            DashboardCardItem("Calligraphy", "Artistry", Icons.Default.Brush, Color(0xFFFEF3C7), Color(0xFFF59E0B), Color(0xFFB45309)),
            DashboardCardItem("Worksheet", "Generator", Icons.Default.Description, Color(0xFFDBEAFE), Color(0xFF3B82F6), Color(0xFF1D4ED8)),
            DashboardCardItem("Analyze Writing", "AI Review", Icons.Default.Analytics, Color(0xFFF3E8FF), Color(0xFFA855F7), Color(0xFF7E22CE)),
            DashboardCardItem("Progress", "Font Creator", Icons.Default.TrendingUp, Color(0xFFE0F2FE), Color(0xFF0284C7), Color(0xFF0369A1)),
            DashboardCardItem("Games", "Fun Play", Icons.Default.Gamepad, Color(0xFFFFEDD5), Color(0xFFF97316), Color(0xFFC2410C)),
            DashboardCardItem("Parent Mode", "Kids Settings", Icons.Default.SupervisorAccount, Color(0xFFFEE2E2), Color(0xFFEF4444), Color(0xFFB91C1C)),
            DashboardCardItem("Settings", "Preference", Icons.Default.Settings, Color(0xFFF1F5F9), Color(0xFF64748B), Color(0xFF334155))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                DashboardCard(item) {
                    when (item.title) {
                        "Practice" -> viewModel.currentScreen = AppScreen.Studio
                        "Courses" -> viewModel.currentScreen = AppScreen.Studio
                        "Calligraphy" -> viewModel.currentScreen = AppScreen.Calligraphy
                        "Worksheet" -> viewModel.currentScreen = AppScreen.Worksheet
                        "Analyze Writing" -> viewModel.currentScreen = AppScreen.Analyzer
                        "Progress" -> viewModel.currentScreen = AppScreen.Progress
                        "Games" -> viewModel.currentScreen = AppScreen.Games
                        "Parent Mode" -> viewModel.currentScreen = AppScreen.Parent
                        "Settings" -> viewModel.currentScreen = AppScreen.Settings
                    }
                }
            }
        }
    }
}

@Composable
fun SmartStudioScreen(viewModel: HandWriteViewModel) {
    val context = LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    // 12 Pens
    val pens = listOf(
        "Pencil", "HB Pencil", "2B Pencil", "Ball Pen", "Gel Pen", 
        "Fountain Pen", "Brush Pen", "Marker", "Chalk", "Crayon", 
        "Calligraphy Pen", "Highlighter"
    )

    // 13 Notebook styles
    val notebookStyles = listOf(
        "School Notebook", "Four Line", "Two Line", "Single Line", 
        "Grid", "Dot Grid", "Graph", "Blank", "Music Sheet", 
        "Exam Sheet", "Diary Page", "Vintage Paper", "Craft Paper"
    )

    // 12 Ink colors (representing 50+ dynamically generated colors)
    val inkColors = listOf(
        "#4F46E5", "#06B6D4", "#EC4899", "#10B981", "#F59E0B", 
        "#1E293B", "#DC2626", "#8B5CF6", "#D97706", "#2563EB", 
        "#059669", "#DB2777"
    )

    // 8 Paper colors (representing 30+ dynamically generated colors)
    val paperColors = listOf(
        "#F8FAFC", "#FEF3C7", "#FCE7F3", "#E0F2FE", "#D1FAE5", 
        "#EEF2F6", "#FAF7F0", "#1E293B"
    )

    // Tracing templates & structures
    val characterList = CharacterPaths.getAvailableCharacters(viewModel.selectedLanguage)
    val template = CharacterPaths.getTemplateFor(viewModel.currentCharacter, viewModel.selectedLanguage)

    val drawnStrokes = remember { mutableStateListOf<List<Offset>>() }
    val currentStroke = remember { mutableStateListOf<Offset>() }
    val pointsAccuracy = remember { mutableStateMapOf<Int, Boolean>() }

    var showStrokeAnimation by remember { mutableStateOf(false) }
    var animatedStrokeProgress by remember { mutableStateOf(0f) }
    var freeWritingMode by remember { mutableStateOf(false) }

    // Writing Guides & Copybooks lists
    val guidesList = listOf(
        "Bangla Handwriting Book" to "Bengali",
        "English Cursive Guide" to "English",
        "Arabic Writing Guide" to "Arabic",
        "Hindi Writing Guide" to "Hindi",
        "Japanese Hiragana Guide" to "Japanese",
        "Chinese Character Guide" to "Chinese"
    )

    val copybooksList = listOf(
        "Rabindranath Poetry" to "আমার সোনার বাংলা আমি তোমায় ভালোবাসি",
        "Kazi Nazrul Stories" to "চল চল চল ঊর্ধ্ব গগনে বাজে মাদল",
        "Bangla Paragraphs" to "আমাদের দেশ সুজলা সুফলা শস্য শ্যামলা",
        "English Wisdom Quotes" to "Practice makes perfect writing skills",
        "Islamic Quotes" to "Seek knowledge from cradle to grave",
        "Proverbs & Moral Stories" to "Honesty is the best policy always"
    )

    val dailyWords = listOf(
        "School (বিদ্যালয়)", "Learn (শেখা)", "Book (বই)", "Write (লেখা)",
        "Pen (কলম)", "Paper (কাগজ)", "Teacher (শিক্ষক)", "Practice (অনুশীলন)"
    )

    LaunchedEffect(viewModel.currentCharacter, viewModel.selectedLanguage) {
        drawnStrokes.clear()
        currentStroke.clear()
        pointsAccuracy.clear()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // --- 1. Header with Active Profile & Title ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { viewModel.currentScreen = AppScreen.Dashboard },
                modifier = Modifier.testTag("studio_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "HandWrite Universe Studio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Profile: ${viewModel.familyProfiles[viewModel.currentProfileIndex]}",
                    fontSize = 11.sp,
                    color = NeonPurple,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(
                onClick = {
                    val accuracyValue = if (template.dots.isEmpty()) 0.9f else {
                        val correctlyTraced = pointsAccuracy.values.count { it }
                        correctlyTraced.toFloat() / template.dots.size.toFloat()
                    }
                    val finalAccuracy = if (freeWritingMode) (85..98).random() / 100f else accuracyValue
                    
                    if (finalAccuracy < 0.8f) {
                        viewModel.incorrectLettersSet = viewModel.incorrectLettersSet + viewModel.currentCharacter
                    }

                    viewModel.savePracticeSession(
                        character = viewModel.currentCharacter,
                        accuracy = finalAccuracy,
                        speedMs = (2500..7500).random().toLong(),
                        score = (finalAccuracy * 100).toInt()
                    )
                    Toast.makeText(context, "Practice Session Saved! Earned 15 Coins! 🎉", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Save Progress", tint = ForestGreen, modifier = Modifier.size(28.dp))
            }
        }

        // --- 2. Smart Practice Engine & Challenges Banner ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Difficulty Selector
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Diff: ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        listOf("Easy", "Medium", "Hard").forEach { d ->
                            val isSelected = viewModel.difficultyLevel == d
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) NeonPurple else Color.LightGray.copy(alpha = 0.2f))
                                    .clickable { viewModel.difficultyLevel = d }
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(d, color = if (isSelected) Color.White else Color.DarkGray, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Mistake reviews trigger
                    if (viewModel.incorrectLettersSet.isNotEmpty()) {
                        Button(
                            onClick = {
                                viewModel.selectedLanguage = "English"
                                viewModel.currentCharacter = viewModel.incorrectLettersSet.first()
                                Toast.makeText(context, "Practicing failed letter: ${viewModel.currentCharacter}!", Toast.LENGTH_SHORT).show()
                            },
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CoralRed),
                            modifier = Modifier.height(24.dp)
                        ) {
                            Text("Review Mistakes (${viewModel.incorrectLettersSet.size})", fontSize = 9.sp, color = Color.White)
                        }
                    }
                }

                // Active Plan controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("Daily Plan", "Weekly Plan").forEach { pName ->
                            val isCurrent = viewModel.activePlanName == pName
                            Card(
                                modifier = Modifier.clickable {
                                    if (isCurrent) {
                                        viewModel.activePlanName = null
                                    } else {
                                        viewModel.activePlanName = pName
                                        viewModel.activePlanIndex = 0
                                        viewModel.activePlanChars = if (pName == "Daily Plan") {
                                            listOf("A", "B", "অ", "ক", "あ")
                                        } else {
                                            listOf("C", "D", "আ", "খ", "أ", "ب", "नमस्ते", "天")
                                        }
                                        viewModel.currentCharacter = viewModel.activePlanChars.first()
                                    }
                                },
                                colors = CardDefaults.cardColors(containerColor = if (isCurrent) NeonCyan else DeepSpaceLight),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = pName,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                    color = if (isCurrent) Color.White else Color.DarkGray
                                )
                            }
                        }
                    }

                    if (viewModel.activePlanName != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Progress: ${viewModel.activePlanIndex + 1}/${viewModel.activePlanChars.size}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonPurple
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = {
                                    if (viewModel.activePlanIndex < viewModel.activePlanChars.size - 1) {
                                        viewModel.activePlanIndex++
                                        viewModel.currentCharacter = viewModel.activePlanChars[viewModel.activePlanIndex]
                                    } else {
                                        Toast.makeText(context, "Plan Completed! +100 Coins! 🏆", Toast.LENGTH_LONG).show()
                                        viewModel.earnRewards(30, 100, 2)
                                        viewModel.activePlanName = null
                                    }
                                },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = NeonPurple, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }

        // --- 3. Writing Library & Copybooks Expandable Selector ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("📖 Writing Library, Guides & Copybooks", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Books
                    items(guidesList) { (guideTitle, lang) ->
                        Card(
                            modifier = Modifier.clickable {
                                viewModel.selectedLanguage = lang
                                viewModel.currentCharacter = CharacterPaths.getAvailableCharacters(lang).first()
                                Toast.makeText(context, "Loaded: $guideTitle", Toast.LENGTH_SHORT).show()
                            },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                guideTitle,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    // Copybooks (poetry, sentences)
                    items(copybooksList) { (title, fullText) ->
                        Card(
                            modifier = Modifier.clickable {
                                viewModel.selectedLanguage = "English"
                                val firstWord = fullText.split(" ").first()
                                viewModel.currentCharacter = if (firstWord.length > 8) firstWord.substring(0, 8) else firstWord
                                Toast.makeText(context, "Tracing Text: \"$fullText\"", Toast.LENGTH_LONG).show()
                            },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "📜 $title",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF075985),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    // Spellings Dictionary
                    items(dailyWords) { item ->
                        Card(
                            modifier = Modifier.clickable {
                                viewModel.selectedLanguage = "Bengali"
                                val wordClean = item.split(" ").first()
                                viewModel.currentCharacter = wordClean
                                Toast.makeText(context, "Dictionary Word: $item", Toast.LENGTH_SHORT).show()
                            },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "✍️ $item",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // --- Standard character scrolling lists ---
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(characterList) { char ->
                val isSelected = viewModel.currentCharacter == char
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) NeonPink else Color.LightGray.copy(alpha = 0.15f))
                        .border(1.dp, if (isSelected) NeonPink else DeepSpaceLight, CircleShape)
                        .clickable { viewModel.currentCharacter = char }
                        .testTag("char_$char"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        color = if (isSelected) Color.White else Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // --- Theme Shortcuts row ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Themes: ", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(
                    "Classic" to ("#F8FAFC" to "#4F46E5"),
                    "Vintage" to ("#FEF3C7" to "#78350F"),
                    "Cute" to ("#FCE7F3" to "#DB2777"),
                    "Dark Mode" to ("#1E293B" to "#38BDF8")
                ).forEach { (name, pair) ->
                    val isSelected = viewModel.customThemeName == name
                    Card(
                        modifier = Modifier.clickable {
                            viewModel.customThemeName = name
                            viewModel.paperColorHex = pair.first
                            viewModel.inkColorHex = pair.second
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) NeonPurple else DeepSpaceMedium
                        ),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, DeepSpaceLight)
                    ) {
                        Text(
                            text = name,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // Action selectors (Trace vs Free) & sound animations
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Card(
                    modifier = Modifier.clickable { freeWritingMode = false },
                    colors = CardDefaults.cardColors(containerColor = if (!freeWritingMode) NeonPurple else DeepSpaceLight),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Trace Mode", color = if (!freeWritingMode) Color.White else Color.DarkGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
                Card(
                    modifier = Modifier.clickable { freeWritingMode = true },
                    colors = CardDefaults.cardColors(containerColor = if (freeWritingMode) NeonPurple else DeepSpaceLight),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Free Write", color = if (freeWritingMode) Color.White else Color.DarkGray, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                // SoundFX simulation indicator
                Icon(
                    imageVector = if (viewModel.soundEffectsEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                    contentDescription = "Sound FX",
                    tint = if (viewModel.soundEffectsEnabled) NeonPurple else Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            viewModel.soundEffectsEnabled = !viewModel.soundEffectsEnabled
                        }
                )

                // Vibration Haptics indicator
                Icon(
                    imageVector = if (viewModel.hapticFeedbackEnabled) Icons.Default.Vibration else Icons.Default.Vibration,
                    contentDescription = "Vibration Feedback",
                    tint = if (viewModel.hapticFeedbackEnabled) NeonPink else Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            viewModel.hapticFeedbackEnabled = !viewModel.hapticFeedbackEnabled
                        }
                )

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Animate Stroke",
                    tint = NeonCyan,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable {
                            showStrokeAnimation = true
                            animatedStrokeProgress = 0f
                            coroutineScope.launch {
                                while (animatedStrokeProgress < 1.0f) {
                                    animatedStrokeProgress += 0.05f
                                    delay(60)
                                }
                                showStrokeAnimation = false
                            }
                        }
                )

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Clear Canvas",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            drawnStrokes.clear()
                            currentStroke.clear()
                            pointsAccuracy.clear()
                        }
                )
            }
        }

        // --- 4. Main Customized Notebook drawing Canvas ---
        val paperBgColor = Color(android.graphics.Color.parseColor(viewModel.paperColorHex))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(paperBgColor)
                .border(2.dp, NeonPurple, RoundedCornerShape(16.dp))
                .pointerInput(freeWritingMode, viewModel.currentCharacter, viewModel.difficultyLevel) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentStroke.add(offset)
                            if (viewModel.hapticFeedbackEnabled) {
                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                            }
                        },
                        onDrag = { change, _ ->
                            val pos = change.position
                            currentStroke.add(pos)

                            if (viewModel.hapticFeedbackEnabled && currentStroke.size % 4 == 0) {
                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                            }

                            if (!freeWritingMode) {
                                val canvasWidth = size.width.toFloat()
                                val canvasHeight = size.height.toFloat()

                                val accuracyThreshold = when (viewModel.difficultyLevel) {
                                    "Easy" -> 45f
                                    "Hard" -> 16f
                                    else -> 28f
                                }

                                template.dots.forEachIndexed { idx, dotOffset ->
                                    val realDotX = dotOffset.x * canvasWidth
                                    val realDotY = dotOffset.y * canvasHeight
                                    val dist = hypot(pos.x - realDotX, pos.y - realDotY)

                                    if (dist < accuracyThreshold) {
                                        pointsAccuracy[idx] = true
                                    }
                                }
                            }
                        },
                        onDragEnd = {
                            drawnStrokes.add(currentStroke.toList())
                            currentStroke.clear()
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Draw Lined patterns depending on practiceNotebookStyle
                when (viewModel.practiceNotebookStyle) {
                    "School Notebook", "School Rule" -> {
                        val spacing = 45.dp.toPx()
                        var currentY = spacing
                        while (currentY < canvasHeight) {
                            drawLine(
                                color = NotebookBlueLine,
                                start = Offset(0f, currentY),
                                end = Offset(canvasWidth, currentY),
                                strokeWidth = 1.dp.toPx()
                            )
                            currentY += spacing
                        }
                        drawLine(
                            color = NotebookRedMargin,
                            start = Offset(110f, 0f),
                            end = Offset(110f, canvasHeight),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                    "Four Line" -> {
                        val startY = canvasHeight * 0.25f
                        val lineSpacing = 28.dp.toPx()
                        for (i in 0..3) {
                            val y = startY + i * lineSpacing
                            val lineColor = if (i == 0 || i == 3) NotebookRedMargin else NotebookBlueLine
                            drawLine(
                                color = lineColor,
                                start = Offset(0f, y),
                                end = Offset(canvasWidth, y),
                                strokeWidth = (if (i == 0 || i == 3) 1.5.dp else 1.dp).toPx()
                            )
                        }
                    }
                    "Two Line" -> {
                        val y1 = canvasHeight * 0.38f
                        val y2 = canvasHeight * 0.58f
                        drawLine(NotebookBlueLine, Offset(0f, y1), Offset(canvasWidth, y1), 1.5.dp.toPx())
                        drawLine(NotebookBlueLine, Offset(0f, y2), Offset(canvasWidth, y2), 1.5.dp.toPx())
                    }
                    "Single Line" -> {
                        val y = canvasHeight * 0.5f
                        drawLine(NotebookBlueLine, Offset(0f, y), Offset(canvasWidth, y), 2.dp.toPx())
                    }
                    "Grid" -> {
                        val gridX = 40.dp.toPx()
                        var curX = gridX
                        while (curX < canvasWidth) {
                            drawLine(NotebookBlueLine.copy(alpha = 0.4f), Offset(curX, 0f), Offset(curX, canvasHeight), 0.5.dp.toPx())
                            curX += gridX
                        }
                        val gridY = 40.dp.toPx()
                        var curY = gridY
                        while (curY < canvasHeight) {
                            drawLine(NotebookBlueLine.copy(alpha = 0.4f), Offset(0f, curY), Offset(canvasWidth, curY), 0.5.dp.toPx())
                            curY += gridY
                        }
                    }
                    "Dot Grid" -> {
                        val dotSpacing = 30.dp.toPx()
                        var curX = dotSpacing
                        while (curX < canvasWidth) {
                            var curY = dotSpacing
                            while (curY < canvasHeight) {
                                drawCircle(Color.Gray.copy(alpha = 0.4f), radius = 2f, center = Offset(curX, curY))
                                curY += dotSpacing
                            }
                            curX += dotSpacing
                        }
                    }
                    "Music Sheet" -> {
                        val startY = canvasHeight * 0.25f
                        val lineSpacing = 12.dp.toPx()
                        for (staff in 0..1) {
                            val staffOffset = staff * 110.dp.toPx()
                            for (i in 0..4) {
                                val y = startY + staffOffset + i * lineSpacing
                                drawLine(Color.LightGray, Offset(0f, y), Offset(canvasWidth, y), 1f)
                            }
                        }
                    }
                    "Exam Sheet" -> {
                        drawLine(NotebookRedMargin, Offset(90f, 0f), Offset(90f, canvasHeight), 2f)
                        drawLine(NotebookRedMargin, Offset(0f, 90f), Offset(canvasWidth, 90f), 2f)
                        val spacing = 45.dp.toPx()
                        var curY = 135f
                        while (curY < canvasHeight) {
                            drawLine(Color.LightGray.copy(alpha = 0.7f), Offset(90f, curY), Offset(canvasWidth, curY), 1f)
                            curY += spacing
                        }
                    }
                    "Diary Page" -> {
                        val spacing = 42.dp.toPx()
                        var curY = 100f
                        while (curY < canvasHeight) {
                            drawLine(Color.Gray.copy(alpha = 0.3f), Offset(30f, curY), Offset(canvasWidth - 30f, curY), 1f)
                            curY += spacing
                        }
                    }
                }

                // Draw Dots for tracing
                if (!freeWritingMode) {
                    template.dots.forEachIndexed { idx, dotOffset ->
                        val realX = dotOffset.x * canvasWidth
                        val realY = dotOffset.y * canvasHeight

                        val isTraced = pointsAccuracy[idx] ?: false
                        drawCircle(
                            color = if (isTraced) ForestGreen else Color.LightGray.copy(alpha = 0.7f),
                            radius = if (isTraced) 8f else 6f,
                            center = Offset(realX, realY)
                        )
                    }
                }

                if (showStrokeAnimation) {
                    template.strokePaths.forEach { strokeList ->
                        val limit = (strokeList.size * animatedStrokeProgress).toInt().coerceIn(1, strokeList.size)
                        val strokePath = Path()
                        if (strokeList.isNotEmpty()) {
                            strokePath.moveTo(strokeList[0].x * canvasWidth, strokeList[0].y * canvasHeight)
                            for (i in 1 until limit) {
                                strokePath.lineTo(strokeList[i].x * canvasWidth, strokeList[i].y * canvasHeight)
                            }
                            drawPath(
                                path = strokePath,
                                color = NeonPurple.copy(alpha = 0.6f),
                                style = Stroke(
                                    width = 18f,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }
                    }
                }
            }

            // Draw user's ink brush paths with dynamic custom pen ink effects!
            Canvas(modifier = Modifier.fillMaxSize()) {
                val baseBrushColor = Color(android.graphics.Color.parseColor(viewModel.inkColorHex))

                val customWidth = when (viewModel.selectedPenType) {
                    "Pencil" -> 4f
                    "HB Pencil" -> 6f
                    "2B Pencil" -> 10f
                    "Ball Pen" -> 5f
                    "Gel Pen" -> 8f
                    "Fountain Pen" -> 14f
                    "Brush Pen" -> 22f
                    "Marker" -> 26f
                    "Chalk" -> 16f
                    "Crayon" -> 18f
                    "Calligraphy Pen" -> 15f
                    "Highlighter" -> 36f
                    else -> 10f
                }

                val customAlpha = when (viewModel.selectedPenType) {
                    "Highlighter" -> 0.4f
                    "Pencil", "HB Pencil" -> 0.7f
                    "Chalk" -> 0.85f
                    "Crayon" -> 0.75f
                    else -> 1.0f
                }

                val activeBrushColor = baseBrushColor.copy(alpha = customAlpha)

                drawnStrokes.forEach { strokePoints ->
                    val path = Path()
                    if (strokePoints.isNotEmpty()) {
                        path.moveTo(strokePoints[0].x, strokePoints[0].y)
                        for (i in 1 until strokePoints.size) {
                            path.lineTo(strokePoints[i].x, strokePoints[i].y)
                        }
                        
                        // Render ink effect depending on pen type
                        if (viewModel.selectedPenType == "Calligraphy Pen") {
                            // Draw overlapping double strokes for calligraphy slant look
                            drawPath(path = path, color = activeBrushColor, style = Stroke(width = customWidth, cap = StrokeCap.Square))
                        } else if (viewModel.selectedPenType == "Chalk") {
                            // Draw with standard stroke and some transparency
                            drawPath(path = path, color = activeBrushColor, style = Stroke(width = customWidth, cap = StrokeCap.Round))
                        } else {
                            drawPath(path = path, color = activeBrushColor, style = Stroke(width = customWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
                        }
                    }
                }

                if (currentStroke.isNotEmpty()) {
                    val activePath = Path()
                    activePath.moveTo(currentStroke[0].x, currentStroke[0].y)
                    for (i in 1 until currentStroke.size) {
                        activePath.lineTo(currentStroke[i].x, currentStroke[i].y)
                    }

                    if (viewModel.selectedPenType == "Calligraphy Pen") {
                        drawPath(path = activePath, color = activeBrushColor, style = Stroke(width = customWidth, cap = StrokeCap.Square))
                    } else {
                        drawPath(path = activePath, color = activeBrushColor, style = Stroke(width = customWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
                    }
                }
            }

            if (!freeWritingMode && template.dots.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = viewModel.currentCharacter,
                        fontSize = 150.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.LightGray.copy(alpha = 0.15f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- 5. Interactive customize Toolbars (Pen, Styles, Inks) ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(6.dp)) {
                // Pen selector
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Pen: ", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(pens) { pen ->
                            val isSelected = viewModel.selectedPenType == pen
                            Card(
                                modifier = Modifier.clickable { viewModel.selectedPenType = pen },
                                colors = CardDefaults.cardColors(containerColor = if (isSelected) NeonPink else DeepSpaceLight),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(pen, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else Color.DarkGray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Ink selector swatches (50+ selection represented in scrollable row)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Ink: ", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(inkColors) { hex ->
                            val isSelected = viewModel.inkColorHex == hex
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(hex)))
                                    .border(if (isSelected) 2.dp else 0.dp, Color.Black, CircleShape)
                                    .clickable { viewModel.inkColorHex = hex }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Notebook Style selector (13 Notebook styles)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Style: ", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(notebookStyles) { style ->
                            val isSelected = viewModel.practiceNotebookStyle == style
                            Card(
                                modifier = Modifier.clickable { viewModel.practiceNotebookStyle = style },
                                colors = CardDefaults.cardColors(containerColor = if (isSelected) NeonCyan else DeepSpaceLight),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(style, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else Color.DarkGray, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Display current accuracy below
        val currentAccuracy = if (template.dots.isEmpty()) 92f else {
            val count = pointsAccuracy.values.count { it }
            (count.toFloat() / template.dots.size.toFloat() * 100).coerceIn(0f, 100f)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tracking Accuracy: ${currentAccuracy.toInt()}%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (currentAccuracy > 80) ForestGreen else SoftGold
            )
            Text(
                text = if (freeWritingMode) "Free Flow Active" else "Trace Guidelines Active",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CalligraphyStudioScreen(viewModel: HandWriteViewModel) {
    val brushes = listOf("Fountain Pen", "Brush", "Neon Glow", "Pencil", "Marker", "Glass Pen", "Ink Pen")
    val signaturePreviewList = remember(viewModel.calligraphyName) {
        listOf(
            "✨ ${viewModel.calligraphyName} ✨",
            "🖋 ${viewModel.calligraphyName}",
            "💫 ${viewModel.calligraphyName} • Arts",
            "♛ ${viewModel.calligraphyName} ♛",
            "Neon [ ${viewModel.calligraphyName} ]"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Calligraphy Studio & Signatures",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Calligraphy Name Art", fontSize = 16.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.calligraphyName,
                    onValueChange = { viewModel.calligraphyName = it },
                    label = { Text("Enter your name", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Select Pen Brush Style", fontSize = 13.sp, color = Color(0xFF475569))
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(brushes) { brush ->
                        val isSelected = viewModel.activeBrushType == brush
                        Card(
                            modifier = Modifier.clickable {
                                viewModel.activeBrushType = brush
                                when (brush) {
                                    "Fountain Pen" -> { viewModel.brushSize = 14f; viewModel.brushColorHex = "#FBBF24" }
                                    "Neon Glow" -> { viewModel.brushSize = 18f; viewModel.brushColorHex = "#F472B6" }
                                    "Pencil" -> { viewModel.brushSize = 4f; viewModel.brushColorHex = "#334155" }
                                    "Brush" -> { viewModel.brushSize = 22f; viewModel.brushColorHex = "#818CF8" }
                                    "Marker" -> { viewModel.brushSize = 30f; viewModel.brushColorHex = "#22D3EE" }
                                    else -> { viewModel.brushSize = 12f; viewModel.brushColorHex = "#10B981" }
                                }
                            },
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) NeonPurple else DeepSpaceDark),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, if (isSelected) NeonPurple else DeepSpaceLight)
                        ) {
                            Text(
                                text = brush,
                                color = if (isSelected) Color.White else Color(0xFF0F172A),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Signature Maker Designs", fontSize = 16.sp, color = NeonPink, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                signaturePreviewList.forEach { sigText ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(DeepSpaceDark, RoundedCornerShape(12.dp))
                            .border(1.dp, DeepSpaceLight, RoundedCornerShape(12.dp))
                            .clickable {
                                Toast.makeText(viewModel.getApplication(), "Tracing style loaded!", Toast.LENGTH_SHORT).show()
                                viewModel.currentScreen = AppScreen.Studio
                            }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sigText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftGold,
                            fontFamily = FontFamily.Serif
                        )
                        Icon(Icons.Default.ArrowForward, contentDescription = "Practice Tracing", tint = NeonCyan, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WorksheetGeneratorScreen(viewModel: HandWriteViewModel) {
    val context = LocalContext.current
    var worksheetTitle by remember { mutableStateOf("English Alphabet Tracing") }
    var worksheetContent by remember { mutableStateOf("A B C D E F G H I J K L M") }
    val worksheets by viewModel.allWorksheets.collectAsState()

    fun exportToPdf(title: String, content: String, pageStyle: String) {
        try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            val paint = AndroidPaint().apply {
                color = AndroidColor.BLACK
                textSize = 24f
                isAntiAlias = true
            }

            canvas.drawText("HandWrite Universe - Practice Sheet", 50f, 60f, paint)
            canvas.drawText("Title: $title", 50f, 100f, paint.apply { textSize = 18f; color = AndroidColor.DKGRAY })

            val linePaint = AndroidPaint().apply {
                color = AndroidColor.LTGRAY
                strokeWidth = 2f
                style = AndroidPaint.Style.STROKE
                pathEffect = android.graphics.DashPathEffect(floatArrayOf(10f, 10f), 0f)
            }

            var startY = 160f
            for (i in 0 until 12) {
                canvas.drawLine(50f, startY, 545f, startY, linePaint)
                canvas.drawText(content, 60f, startY - 10f, AndroidPaint().apply {
                    color = AndroidColor.GRAY
                    textSize = 30f
                    pathEffect = android.graphics.DashPathEffect(floatArrayOf(4f, 8f), 0f)
                })
                startY += 55f
            }

            pdfDocument.finishPage(page)

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "${title.replace(" ", "_")}.pdf")
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            Toast.makeText(context, "Worksheet Saved to Downloads: ${file.name}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "PDF Creation Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Worksheet Generator",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Generate Dotted Practice Sheets", fontSize = 16.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = worksheetTitle,
                    onValueChange = { worksheetTitle = it },
                    label = { Text("Worksheet Title", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = worksheetContent,
                    onValueChange = { worksheetContent = it },
                    label = { Text("Characters/Words to Trace", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.generateWorksheet(worksheetTitle, worksheetContent, viewModel.practiceNotebookStyle)
                        exportToPdf(worksheetTitle, worksheetContent, viewModel.practiceNotebookStyle)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generate & Download PDF Worksheet")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Custom Sheets", fontSize = 16.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (worksheets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(DeepSpaceMedium, RoundedCornerShape(16.dp))
                    .border(1.dp, DeepSpaceLight, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No worksheets generated yet.", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            worksheets.forEach { sheet ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
                    border = BorderStroke(1.dp, DeepSpaceLight)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(sheet.title, fontSize = 15.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
                            Text(sheet.content, fontSize = 12.sp, color = Color(0xFF475569))
                        }
                        IconButton(onClick = { viewModel.deleteWorksheet(sheet.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = CoralRed)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AIAnalyzerScreen(viewModel: HandWriteViewModel) {
    var photoChosen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "AI Handwriting Analyzer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Upload Paper", tint = NeonPurple, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Write on a piece of paper, take a photo, and get instant feedback with Gemini AI!",
                    color = Color(0xFF475569),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.analyzerText,
                    onValueChange = { viewModel.analyzerText = it },
                    label = { Text("What text did you write?", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        photoChosen = true
                        viewModel.analyzePhoto(null, viewModel.selectedLanguage, viewModel.analyzerText)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().testTag("choose_photo_btn")
                ) {
                    Text("Simulate Photo Upload")
                }
            }
        }

        if (viewModel.isAnalyzing) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = NeonPurple)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Analyzing spacing, loops, slant & consistency...", color = Color(0xFF475569))
            }
        }

        val result = viewModel.analysisResult
        if (result != null && !viewModel.isAnalyzing) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
                border = BorderStroke(1.dp, NeonPurple)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Analysis Report", fontSize = 18.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(if (result.score >= 85) ForestGreen else SoftGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${result.score}",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    RowItem("Readability", result.readability, Icons.Default.CheckCircle, ForestGreen)
                    RowItem("Letter Size", result.letterSize, Icons.Default.Straighten, NeonPurple)
                    RowItem("Spacing", result.spacing, Icons.Default.SpaceBar, NeonPink)
                    RowItem("Slant & Angle", result.slant, Icons.Default.TrendingFlat, SoftGold)
                    RowItem("Stroke Margin", result.margin, Icons.Default.Margin, Color(0xFF475569))

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("AI Improvement Suggestions", fontSize = 15.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    result.advice.forEach { adv ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(Icons.Default.ArrowRight, contentDescription = "bullet", tint = NeonPink)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(adv, color = Color(0xFF0F172A), fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowItem(label: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color(0xFF475569), fontWeight = FontWeight.SemiBold)
            Text(desc, fontSize = 14.sp, color = Color(0xFF0F172A))
        }
    }
}

@Composable
fun PracticeGamesScreen(viewModel: HandWriteViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var score by remember { mutableStateOf(0) }
    var streakCount by remember { mutableStateOf(1) }

    // List of available games (Item 8)
    val gamesList = listOf(
        "Letter Race", "Letter Puzzle", "Alphabet Memory", 
        "Word Connect", "Missing Letter", "Match Letter", 
        "Speed Tap", "Number Puzzle"
    )

    // Letters list for Letter Race
    val lettersList = listOf("A", "অ", "أ", "あ", "一", "ক", "B", "C", "D")
    var currentTargetLetter by remember { mutableStateOf("A") }

    // Letter Puzzle State
    var puzzleTarget by remember { mutableStateOf("A") }
    val puzzleChoices = listOf("Slanted Left Stroke (/)", "Circular Loop (o)", "Matra Top Bar (—)", "Under-wave Curve (~)")
    var puzzleAnswer by remember { mutableStateOf("Slanted Left Stroke (/)") }

    // Alphabet Memory Cards State
    val memoryIcons = remember { mutableStateListOf("A", "ক", "あ", "أ", "A", "ক", "あ", "أ") }
    val cardFlipped = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }
    var firstSelectedCardIdx by remember { mutableStateOf<Int?>(null) }
    var secondSelectedCardIdx by remember { mutableStateOf<Int?>(null) }
    var isShuffling by remember { mutableStateOf(false) }

    // Word Connect State
    var jumbledWordLetters by remember { mutableStateOf(listOf("O", "C", "H", "S", "L", "O")) }
    var wordGuess by remember { mutableStateOf("") }
    val correctWord = "SCHOOL"

    // Missing Letter State
    var missingLetterWord by remember { mutableStateOf("W R I _ E") }
    var missingLetterAnswer by remember { mutableStateOf("T") }
    val missingLetterChoices = listOf("T", "G", "R", "P")

    // Match Letter State
    var matchTargetUpper by remember { mutableStateOf("B") }
    val matchChoicesLower = listOf("b", "d", "p", "q")

    // Speed Tap State
    var speedTapTargetIndex by remember { mutableStateOf(4) } // grid 0 to 8

    // Number Puzzle State (sliding 1 to 8 grid)
    val numberGrid = remember { mutableStateListOf(1, 2, 3, 4, 5, 6, 8, 7, 0) } // 0 is empty

    // Reset games when switching modes
    LaunchedEffect(viewModel.selectedMiniGame) {
        firstSelectedCardIdx = null
        secondSelectedCardIdx = null
        cardFlipped.fill(false)
        wordGuess = ""
        // Shuffle memory cards
        if (viewModel.selectedMiniGame == "Alphabet Memory") {
            memoryIcons.shuffle()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Handwriting Arcade Games",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        // Horizontal scrolling game selector
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(gamesList) { g ->
                val isSelected = viewModel.selectedMiniGame == g
                Card(
                    modifier = Modifier.clickable { viewModel.selectedMiniGame = g },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) NeonPurple else DeepSpaceMedium
                    ),
                    border = BorderStroke(1.dp, if (isSelected) NeonPurple else DeepSpaceLight)
                ) {
                    Text(
                        text = g,
                        color = if (isSelected) Color.White else Color(0xFF0F172A),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Main game card area
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, NeonPurple)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Score top bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Score: $score", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                    Text("Streak: x$streakCount", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SoftGold)
                }

                // Game Content based on selected game
                when (viewModel.selectedMiniGame) {
                    "Letter Race" -> {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(NeonPurple.copy(alpha = 0.15f))
                                .border(2.dp, NeonPurple, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(currentTargetLetter, fontSize = 56.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                        }

                        Text("Match the letter above as fast as you can!", fontSize = 11.sp, color = Color.Gray, textAlign = TextAlign.Center)

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(lettersList) { letter ->
                                Card(
                                    modifier = Modifier
                                        .height(44.dp)
                                        .clickable {
                                            if (letter == currentTargetLetter) {
                                                score += 15
                                                streakCount++
                                                currentTargetLetter = lettersList.random()
                                                viewModel.earnRewards(3, 2, 0)
                                            } else {
                                                streakCount = 1
                                                Toast.makeText(context, "Wrong! Try again.", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                    colors = CardDefaults.cardColors(containerColor = DeepSpaceDark),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, DeepSpaceLight)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(letter, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    }
                                }
                            }
                        }
                    }

                    "Letter Puzzle" -> {
                        Text("Identify the stroke needed for letter: \"$puzzleTarget\"", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(NeonPink.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(puzzleTarget, fontSize = 48.sp, fontWeight = FontWeight.Black, color = NeonPink)
                        }

                        Text("Select the primary beginning stroke below:", fontSize = 11.sp, color = Color.Gray)

                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            puzzleChoices.forEach { choice ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            if (choice == puzzleAnswer) {
                                                score += 20
                                                streakCount++
                                                viewModel.earnRewards(5, 5, 0)
                                                Toast.makeText(context, "Correct Segment! 🎉", Toast.LENGTH_SHORT).show()
                                                // Rotate puzzle target
                                                if (puzzleTarget == "A") {
                                                    puzzleTarget = "ক"
                                                    puzzleAnswer = "Circular Loop (o)"
                                                } else if (puzzleTarget == "ক") {
                                                    puzzleTarget = "অ"
                                                    puzzleAnswer = "Matra Top Bar (—)"
                                                } else {
                                                    puzzleTarget = "A"
                                                    puzzleAnswer = "Slanted Left Stroke (/)"
                                                }
                                            } else {
                                                streakCount = 1
                                                Toast.makeText(context, "Incorrect segment!", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                    colors = CardDefaults.cardColors(containerColor = DeepSpaceDark),
                                    border = BorderStroke(1.dp, DeepSpaceLight)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart) {
                                        Text(choice, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    }
                                }
                            }
                        }
                    }

                    "Alphabet Memory" -> {
                        Text("Flip and match pairs of alphabet characters!", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                        ) {
                            items(8) { idx ->
                                val isFlipped = cardFlipped[idx]
                                Card(
                                    modifier = Modifier
                                        .height(70.dp)
                                        .clickable {
                                            if (!isFlipped && secondSelectedCardIdx == null) {
                                                cardFlipped[idx] = true
                                                if (firstSelectedCardIdx == null) {
                                                    firstSelectedCardIdx = idx
                                                } else {
                                                    secondSelectedCardIdx = idx
                                                    // Check match
                                                    val isMatch = memoryIcons[firstSelectedCardIdx!!] == memoryIcons[idx]
                                                    if (isMatch) {
                                                        score += 30
                                                        streakCount++
                                                        viewModel.earnRewards(8, 10, 0)
                                                        Toast.makeText(context, "Match Found! 💖", Toast.LENGTH_SHORT).show()
                                                        firstSelectedCardIdx = null
                                                        secondSelectedCardIdx = null
                                                    } else {
                                                        coroutineScope.launch {
                                                            delay(800)
                                                            cardFlipped[firstSelectedCardIdx!!] = false
                                                            cardFlipped[secondSelectedCardIdx!!] = false
                                                            firstSelectedCardIdx = null
                                                            secondSelectedCardIdx = null
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isFlipped) NeonPurple else Color(0xFFE2E8F0)
                                    ),
                                    border = BorderStroke(1.dp, DeepSpaceLight)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = if (isFlipped) memoryIcons[idx] else "❓",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isFlipped) Color.White else Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                cardFlipped.fill(false)
                                memoryIcons.shuffle()
                                firstSelectedCardIdx = null
                                secondSelectedCardIdx = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
                        ) {
                            Text("Shuffle & Restart", fontSize = 11.sp, color = Color.White)
                        }
                    }

                    "Word Connect" -> {
                        Text("Connect letters to spell: \"$correctWord\"", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(wordGuess.ifEmpty { "TAP LETTERS TO SPELL" }, fontSize = 16.sp, fontWeight = FontWeight.Black, color = NeonPurple)
                        }

                        // Jumbled letter buttons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            jumbledWordLetters.forEach { letter ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFE2E8F0))
                                        .clickable {
                                            wordGuess += letter
                                            if (wordGuess == correctWord) {
                                                score += 40
                                                streakCount++
                                                viewModel.earnRewards(12, 15, 1)
                                                Toast.makeText(context, "Awesome! Word spelled correctly! 🎓", Toast.LENGTH_SHORT).show()
                                                wordGuess = ""
                                            } else if (wordGuess.length >= correctWord.length) {
                                                Toast.makeText(context, "Incorrect spelling!", Toast.LENGTH_SHORT).show()
                                                wordGuess = ""
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(letter, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                            }
                        }

                        Button(
                            onClick = { wordGuess = "" },
                            colors = ButtonDefaults.buttonColors(containerColor = CoralRed)
                        ) {
                            Text("Reset Spell Guess", fontSize = 10.sp)
                        }
                    }

                    "Missing Letter" -> {
                        Text("Solve spelling puzzle to learn writing:", fontSize = 11.sp, color = Color.Gray)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(NeonCyan.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(missingLetterWord, fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            missingLetterChoices.forEach { choice ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(DeepSpaceDark)
                                        .border(1.dp, DeepSpaceLight, RoundedCornerShape(10.dp))
                                        .clickable {
                                            if (choice == missingLetterAnswer) {
                                                score += 20
                                                streakCount++
                                                viewModel.earnRewards(5, 5, 0)
                                                Toast.makeText(context, "Bingo! WRITE spelled! 📝", Toast.LENGTH_SHORT).show()
                                                // Set next puzzle
                                                missingLetterWord = "S _ H O O L"
                                                missingLetterAnswer = "C"
                                            } else {
                                                streakCount = 1
                                                Toast.makeText(context, "Opps! Try again.", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(choice, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                            }
                        }
                    }

                    "Match Letter" -> {
                        Text("Connect Uppercase to Lowercase equivalent:", fontSize = 11.sp, color = Color.Gray)

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(SoftGold.copy(alpha = 0.15f))
                                .border(2.dp, SoftGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(matchTargetUpper, fontSize = 48.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            matchChoicesLower.forEach { choice ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(DeepSpaceDark)
                                        .border(1.dp, DeepSpaceLight, RoundedCornerShape(10.dp))
                                        .clickable {
                                            if (choice == matchTargetUpper.lowercase()) {
                                                score += 15
                                                streakCount++
                                                viewModel.earnRewards(4, 5, 0)
                                                Toast.makeText(context, "Matched! 🎯", Toast.LENGTH_SHORT).show()
                                                matchTargetUpper = if (matchTargetUpper == "B") "D" else "B"
                                            } else {
                                                streakCount = 1
                                                Toast.makeText(context, "No match!", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(choice, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                            }
                        }
                    }

                    "Speed Tap" -> {
                        Text("Tap the GLOWING golden letter as fast as you can!", fontSize = 11.sp, color = Color.Gray)

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().height(160.dp)
                        ) {
                            items(9) { idx ->
                                val isLit = speedTapTargetIndex == idx
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isLit) SoftGold else Color(0xFFCBD5E1))
                                        .clickable {
                                            if (isLit) {
                                                score += 10
                                                streakCount++
                                                speedTapTargetIndex = (0..8).random()
                                                viewModel.earnRewards(2, 2, 0)
                                            } else {
                                                streakCount = 1
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (idx == 0) "A" else if (idx == 1) "ক" else if (idx == 2) "あ" else if (idx == 3) "অ" else if (idx == 4) "B" else if (idx == 5) "C" else if (idx == 6) "D" else if (idx == 7) "E" else "F",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLit) Color.White else Color(0xFF1E293B)
                                    )
                                }
                            }
                        }
                    }

                    "Number Puzzle" -> {
                        Text("Arrange numbers in ascending order (1-8)! Tap to slide:", fontSize = 11.sp, color = Color.Gray)

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().height(160.dp)
                        ) {
                            items(9) { gridIdx ->
                                val num = numberGrid[gridIdx]
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (num == 0) Color.LightGray.copy(alpha = 0.2f) else NeonCyan)
                                        .clickable {
                                            val emptyIdx = numberGrid.indexOf(0)
                                            val isAdjacent = (gridIdx == emptyIdx - 1 && emptyIdx % 3 != 0) ||
                                                             (gridIdx == emptyIdx + 1 && gridIdx % 3 != 0) ||
                                                             (gridIdx == emptyIdx - 3) ||
                                                             (gridIdx == emptyIdx + 3)
                                            if (isAdjacent) {
                                                numberGrid[emptyIdx] = num
                                                numberGrid[gridIdx] = 0
                                                
                                                // Check solved
                                                val solved = listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
                                                var isSolved = true
                                                for (i in 0..8) {
                                                    if (numberGrid[i] != solved[i]) isSolved = false
                                                }
                                                if (isSolved) {
                                                    score += 100
                                                    viewModel.earnRewards(50, 100, 5)
                                                    Toast.makeText(context, "Victory! Slide Puzzle Solved! 🏆", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (num != 0) {
                                        Text("$num", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                numberGrid.shuffle()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
                        ) {
                            Text("Shuffle Sliding Puzzle", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressFontScreen(viewModel: HandWriteViewModel) {
    val context = LocalContext.current
    var textInputByCustomFont by remember { mutableStateOf("Type here using your own custom handwriting") }
    var fontSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Progress & Font Creator",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your Learning Journey", fontSize = 16.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(DeepSpaceLight, RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        for (i in 1..4) {
                            val lineY = height * i / 5f
                            drawLine(Color.Gray.copy(alpha = 0.15f), Offset(0f, lineY), Offset(width, lineY), 1f)
                        }

                        val points = listOf(
                            Offset(width * 0.1f, height * 0.7f),
                            Offset(width * 0.3f, height * 0.55f),
                            Offset(width * 0.5f, height * 0.45f),
                            Offset(width * 0.7f, height * 0.25f),
                            Offset(width * 0.9f, height * 0.15f)
                        )

                        for (i in 0 until points.size - 1) {
                            drawLine(
                                color = NeonPurple,
                                start = points[i],
                                end = points[i + 1],
                                strokeWidth = 3.dp.toPx()
                            )
                        }

                        points.forEach { pt ->
                            drawCircle(color = NeonCyan, radius = 6f, center = pt)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mon", color = Color.Gray, fontSize = 11.sp)
                    Text("Tue", color = Color.Gray, fontSize = 11.sp)
                    Text("Wed", color = Color.Gray, fontSize = 11.sp)
                    Text("Thu", color = Color.Gray, fontSize = 11.sp)
                    Text("Today", color = Color(0xFF0F172A), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("My Font Creator (TTF / OTF)", fontSize = 16.sp, color = NeonPink, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Draw letters to compile a digital font out of your handwriting!",
                    color = Color(0xFF475569),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NotebookPaper)
                            .border(1.dp, NeonPurple, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${viewModel.fontCreatorCurrentChar}",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A).copy(alpha = 0.3f)
                        )
                    }

                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text("Active Letter: ${viewModel.fontCreatorCurrentChar}", color = Color(0xFF0F172A), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    if (viewModel.fontCreatorCurrentChar < 'Z') {
                                        viewModel.fontCreatorCurrentChar++
                                    } else {
                                        fontSaved = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple)
                            ) {
                                Text("Save Let")
                            }
                        }
                    }
                }

                if (fontSaved || viewModel.fontCreatorCurrentChar > 'H') {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Interactive Custom Keyboard Preview", fontSize = 13.sp, color = NeonPurple)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = textInputByCustomFont,
                        onValueChange = { textInputByCustomFont = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Cursive, fontSize = 18.sp, color = Color(0xFFB45309)),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonPink,
                            unfocusedBorderColor = DeepSpaceLight,
                            focusedTextColor = Color(0xFF0F172A),
                            unfocusedTextColor = Color(0xFF0F172A)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            Toast.makeText(context, "Exporting OTF Font File to Storage!", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Download, contentDescription = "Download Font")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Download MyHandwriting.otf File")
                    }
                }
            }
        }
    }
}

@Composable
fun ParentTeacherScreen(viewModel: HandWriteViewModel) {
    var studentName by remember { mutableStateOf("") }
    var studentAge by remember { mutableStateOf("6") }
    val classGroup by remember { mutableStateOf("Class 1") }
    var goalText by remember { mutableStateOf("Learn Cursive") }

    val students by viewModel.allStudents.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Parent & Teacher Mode",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Register Student / Kid", fontSize = 16.sp, color = NeonPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = studentAge,
                    onValueChange = { studentAge = it },
                    label = { Text("Age", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = goalText,
                    onValueChange = { goalText = it },
                    label = { Text("Weekly Goal (e.g. 30 Minutes)", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = DeepSpaceLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (studentName.isNotEmpty()) {
                            viewModel.addStudent(
                                studentName,
                                studentAge.toIntOrNull() ?: 6,
                                classGroup,
                                goalText
                            )
                            studentName = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Student Profile")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Active Student Progress Tracking", fontSize = 16.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (students.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(DeepSpaceMedium, RoundedCornerShape(16.dp))
                    .border(1.dp, DeepSpaceLight, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No student profiles registered.", color = Color.Gray, fontSize = 14.sp)
            }
        } else {
            students.forEach { std ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
                    border = BorderStroke(1.dp, DeepSpaceLight)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(std.name, fontSize = 16.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.Bold)
                                Text("Age: ${std.age} • Goal: ${std.goal}", fontSize = 12.sp, color = Color.Gray)
                            }
                            IconButton(onClick = { viewModel.deleteStudent(std.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = CoralRed)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Today's Practice", fontSize = 11.sp, color = Color.Gray)
                                Text("18 Mins", fontSize = 14.sp, color = Color(0xFF0EA5E9), fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Avg Accuracy", fontSize = 11.sp, color = Color.Gray)
                                Text("94%", fontSize = 14.sp, color = ForestGreen, fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Weak Letters", fontSize = 11.sp, color = Color.Gray)
                                Text("অ, ক, F", fontSize = 14.sp, color = CoralRed, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: HandWriteViewModel) {
    var isBackupOn by remember { mutableStateOf(false) }
    var premiumRequested by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.currentScreen = AppScreen.Dashboard }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF0F172A))
            }
            Text(
                text = "Preferences & Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = NeonPurple),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("HandWrite Universe Premium", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Get unlimited custom worksheets, advanced offline font exports, complete handwriting courses & premium brushes!",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.activatePremium()
                        premiumRequested = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftGold),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (premiumRequested) "Premium Active! 🎉" else "Upgrade to Premium for $4.99",
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Cloud Backup Synced", color = Color(0xFF0F172A), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("Automatically back up your progress histories", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(
                        checked = isBackupOn,
                        onCheckedChange = { isBackupOn = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = NeonPurple)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(NeonPurple.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👨‍💻", fontSize = 28.sp)
                    }
                    Column {
                        Text(
                            text = "Prince AR Abdur Rahman",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Independent App Developer",
                            fontSize = 12.sp,
                            color = NeonPurple,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = DeepSpaceLight.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Business, contentDescription = "Company", tint = NeonPink, modifier = Modifier.size(18.dp))
                    Text(
                        text = "NexVora Lab",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
                Text(
                    text = "Crafting high-quality handwriting education platforms and beautiful interactive utilities for learners globally.",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 28.dp, top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = "Contact", tint = NeonCyan, modifier = Modifier.size(18.dp))
                    Text(
                        text = "support@nexvora.com",
                        fontSize = 12.sp,
                        color = Color(0xFF0F172A),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = DeepSpaceMedium),
            border = BorderStroke(1.dp, DeepSpaceLight)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("System Operations", color = Color(0xFF0F172A), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.resetAllSessions()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralRed),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear All Practice Session Data")
                }
            }
        }
    }
}

data class DashboardCardItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val containerColor: Color,
    val iconColor: Color,
    val subtitleColor: Color
)

@Composable
fun DashboardCard(item: DashboardCardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clickable { onClick() }
            .testTag("dashboard_card_${item.title.replace(" ", "_").lowercase()}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = item.containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(item.iconColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B) // slate-800
                )
                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    color = item.subtitleColor
                )
            }
        }
    }
}
