package com.abhi.classconnect.sync

import android.content.SharedPreferences
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.abhi.classconnect.data.mappers.toLessonEntity
import com.abhi.classconnect.data.mappers.toStudentRecordEntity
import com.abhi.classconnect.data.models.SyncStatus
import com.abhi.classconnect.data.repository.AttendanceRepository
import com.abhi.classconnect.data.repository.LessonsRepository
import com.abhi.classconnect.sync.conflictResolver.ConflictResolver
import com.abhi.classconnect.utils.SYNC_STATUS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class SyncEngine(
    private val sharedPreferences: SharedPreferences,
    private val conflictResolver: ConflictResolver,
    private val workManager: WorkManager,
    private val workRequest: WorkRequest,
    private val lessonsRepository: LessonsRepository,
    private val attendanceRepository: AttendanceRepository,
) {

    val isSyncPending = sharedPreferences.getBoolean(SYNC_STATUS, false)

    init {
        enqueueWorkRequest()
    }

    fun enqueueWorkRequest() {
        if (isSyncPending) {
            workManager.enqueue(workRequest)
        }
    }

    suspend fun performSync() = withContext(Dispatchers.IO) {
        try {
            // push local changes to server
            syncLessonsToServer()
            syncStudentsToServer()

            // Pull latest updates from server
            fetchStudentsFromServer()
            fetchLessonsFromServer()
        } catch (e: Exception) {
        }
    }


    private suspend fun syncLessonsToServer() {
        val localLessons = lessonsRepository.getPendingSyncLessons()
        localLessons.forEach { localLesson ->
            try {
                val isSuccess = lessonsRepository.uploadLessonToServer(localLesson)
                val updated =
                    localLesson.copy(syncStatus = if (isSuccess) SyncStatus.SYNCED else SyncStatus.FAILED)
                lessonsRepository.updateLessonSyncStatusInLocal(
                    updated.toLessonEntity()
                )
            } catch (e: Exception) {
            }
        }
    }

    private suspend fun fetchLessonsFromServer() {
        val remoteLessons = lessonsRepository.getAllLessonsFromServer()

       remoteLessons.forEach {remoteLesson ->
            val localLesson = lessonsRepository.getLessonFromLocal(remoteLesson.id)
            if (localLesson == null) {
                // its a new lesson coming from server ,so we saving in local DB
                lessonsRepository.saveLessonInLocal(remoteLesson.toLessonEntity())
            } else {
                // conflict resolution
                val resolved = conflictResolver.resolveLessonConflict(
                    remoteLesson = remoteLesson,
                    localLesson = localLesson
                )
                lessonsRepository.saveLessonInLocal(resolved)
            }
        }
    }


    private suspend fun syncStudentsToServer() {
        val pendingStudents = attendanceRepository.getPendingSyncStudentsRecords()
        pendingStudents.forEach { localStudentRecord ->
            try {
                val isSuccess = attendanceRepository.uploadStudentRecordToServer(localStudentRecord)
                val updated =
                    localStudentRecord.copy(syncStatus = if (isSuccess) SyncStatus.SYNCED else SyncStatus.FAILED)
                attendanceRepository.updateStudentSyncStatusInLocal(
                    updated.toStudentRecordEntity()
                )
            } catch (e: Exception) { }
        }
    }

    private suspend fun fetchStudentsFromServer() {
        val remoteRecords = attendanceRepository.getAllStudentRecordsFromServer()

        remoteRecords.forEach { remoteRecord ->
            val localRecord = attendanceRepository.getStudentRecordFromLocal(remoteRecord.id)
            if (localRecord == null) {
                attendanceRepository.saveStudentRecordInLocal(remoteRecord.toStudentRecordEntity())
            } else {
                val resolved = conflictResolver.resolveStudentRecordConflict(
                    remoteRecord = remoteRecord,
                    localRecord = localRecord
                )
                attendanceRepository.saveStudentRecordInLocal(resolved)
            }
        }
    }
}