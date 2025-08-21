package com.abhi.classconnect.data.repository

import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.ui.entities.LessonInfo
import kotlinx.coroutines.flow.Flow

interface LessonsRepository {

    // Local Dbb Methods
    fun observeAllLessons() : Flow<List<LessonInfo>>
    fun getPendingSyncLessons() : List<LessonDto>
    suspend fun saveLessonInLocal(lesson: Lesson)
    suspend fun getLessonFromLocal(id : String) : Lesson?
    suspend fun updateLessonSyncStatusInLocal(lesson: Lesson)


    // Server Methods
    suspend fun getAllLessonsFromServer() : List<LessonDto>
    suspend fun uploadLessonToServer(lesson: LessonDto) : Boolean



    // Network Status
    fun getNetworkStatus() : Flow<Boolean>



}