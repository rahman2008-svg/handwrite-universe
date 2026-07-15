package com.example.data.repository

import com.example.data.database.HandWriteDao
import com.example.data.model.UserProfile
import com.example.data.model.PracticeSession
import com.example.data.model.CustomWorksheet
import com.example.data.model.StudentProfile
import kotlinx.coroutines.flow.Flow

class HandWriteRepository(private val dao: HandWriteDao) {
    val userProfile: Flow<UserProfile?> = dao.getUserProfile()
    val allSessions: Flow<List<PracticeSession>> = dao.getAllSessions()
    val allWorksheets: Flow<List<CustomWorksheet>> = dao.getAllWorksheets()
    val allStudents: Flow<List<StudentProfile>> = dao.getAllStudents()

    suspend fun saveUserProfile(profile: UserProfile) {
        dao.insertOrUpdateProfile(profile)
    }

    suspend fun addPracticeSession(session: PracticeSession) {
        dao.insertSession(session)
    }

    suspend fun clearSessions() {
        dao.clearAllSessions()
    }

    suspend fun addWorksheet(worksheet: CustomWorksheet) {
        dao.insertWorksheet(worksheet)
    }

    suspend fun deleteWorksheet(id: Long) {
        dao.deleteWorksheet(id)
    }

    suspend fun addStudent(student: StudentProfile) {
        dao.insertStudent(student)
    }

    suspend fun deleteStudent(id: Long) {
        dao.deleteStudent(id)
    }
}
