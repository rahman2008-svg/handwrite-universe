package com.example.data.database

import androidx.room.*
import com.example.data.model.UserProfile
import com.example.data.model.PracticeSession
import com.example.data.model.CustomWorksheet
import com.example.data.model.StudentProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface HandWriteDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfile)

    // Practice Sessions
    @Query("SELECT * FROM practice_session ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PracticeSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PracticeSession)

    @Query("DELETE FROM practice_session")
    suspend fun clearAllSessions()

    // Custom Worksheets
    @Query("SELECT * FROM custom_worksheet ORDER BY timestamp DESC")
    fun getAllWorksheets(): Flow<List<CustomWorksheet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorksheet(worksheet: CustomWorksheet)

    @Query("DELETE FROM custom_worksheet WHERE id = :id")
    suspend fun deleteWorksheet(id: Long)

    // Student Profiles
    @Query("SELECT * FROM student_profile ORDER BY id DESC")
    fun getAllStudents(): Flow<List<StudentProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentProfile)

    @Query("DELETE FROM student_profile WHERE id = :id")
    suspend fun deleteStudent(id: Long)
}
