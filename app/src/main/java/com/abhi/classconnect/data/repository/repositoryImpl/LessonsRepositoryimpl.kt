package com.abhi.classconnect.data.repository.repositoryImpl

import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.local.ClassroomDAO
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.local.sharedPref.SharedPrefLocalDataSourceImpl
import com.abhi.classconnect.data.mappers.toLessonDto
import com.abhi.classconnect.data.mappers.toUiLesson
import com.abhi.classconnect.data.remote.FakeRemoteDataSource
import com.abhi.classconnect.data.repository.LessonsRepository
import com.abhi.classconnect.sync.InternetConnectivityObserver
import com.abhi.classconnect.ui.entities.LessonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LessonsRepositoryImpl(
    private val api: FakeRemoteDataSource,
    private val dao: ClassroomDAO,
    private val internetConnectivityObserver: InternetConnectivityObserver,
    private val sharedPrefLocalDataSource: SharedPrefLocalDataSourceImpl
) : LessonsRepository {

    private val isConnected = internetConnectivityObserver.isConnected.value

    // Local Db Methods
    override fun observeAllLessons(): Flow<List<LessonInfo>> {
        return dao.observeAllLessons().map { list -> list.map { it.toUiLesson() } }
    }

    override fun getPendingSyncLessons(): List<LessonDto> {
        return dao.getPendingSyncLessons().map { it.toLessonDto() }
    }

    override fun getNetworkStatus(): Flow<Boolean> {
        return internetConnectivityObserver.observeNetworkStatus()
    }

    override suspend fun saveLessonInLocal(lesson: Lesson) {
        dao.saveLessonInLocal(lesson)
        if (isConnected) uploadLessonToServer(lesson.toLessonDto())
    }

    override suspend fun getLessonFromLocal(id: String): Lesson? {
        return dao.getLessonById(id)
    }

    override suspend fun updateLessonSyncStatusInLocal(
        lesson: Lesson
    ) {
        dao.updateLessonSyncStatusInLocal(lesson)
    }

    // Server methods
    override suspend fun getAllLessonsFromServer(): List<LessonDto> {
        // Here we will return lessons from server
        return listOf()
    }

    override suspend fun uploadLessonToServer(lesson: LessonDto): Boolean {
        val isUploaded = api.uploadLessonToServer(lesson)
        return isUploaded
    }
}