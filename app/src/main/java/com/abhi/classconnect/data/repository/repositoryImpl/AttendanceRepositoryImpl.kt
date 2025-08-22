package com.abhi.classconnect.data.repository.repositoryImpl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.abhi.classconnect.data.dto.students.StudentRecordDto
import com.abhi.classconnect.data.local.ClassroomDAO
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.data.mappers.toStudentRecordDto
import com.abhi.classconnect.data.mappers.toStudentUi
import com.abhi.classconnect.data.remote.FakeRemoteDataSource
import com.abhi.classconnect.data.repository.AttendanceRepository
import com.abhi.classconnect.sync.InternetConnectivityObserver
import com.abhi.classconnect.ui.entities.StudentRecordInfo
import com.abhi.classconnect.utils.SYNC_STATUS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AttendanceRepositoryImpl(
    private val api: FakeRemoteDataSource,
    private val dao: ClassroomDAO,
    private val internetConnectivityObserver: InternetConnectivityObserver,
    private val sharedPreferences: SharedPreferences
) : AttendanceRepository {


    private val isConnected = internetConnectivityObserver.isConnected.value

    override fun observeStudentsRecords(): Flow<List<StudentRecordInfo>> {
        return dao.observeStudentsRecords().map { it.map { it.toStudentUi() } }
    }

    override suspend fun saveStudentRecordInLocal(record: StudentRecord) {
        dao.saveStudentRecordInLocal(record)
        if (isConnected) uploadStudentRecordToServer(record.toStudentRecordDto())
    }

    override fun getPendingSyncStudentsRecords(): List<StudentRecordDto> {
        return dao.getPendingSyncStudentsRecords().map { it.toStudentRecordDto() }
    }


    override suspend fun getStudentRecordFromLocal(id: String): StudentRecord? {
        return dao.getStudentRecordById(id)
    }

    override suspend fun updateStudentSyncStatusInLocal(
        studentRecord: StudentRecord
    ) {
        dao.updateStudentSyncStatusInLocal(studentRecord)
    }

    override suspend fun uploadStudentRecordToServer(record: StudentRecordDto): Boolean {
       val isUploaded =  api.uploadStudentRecordToServer(record)
       if(isUploaded.not()) updateSyncStatusSharedPref(isUploaded)
        return isUploaded
    }

    override suspend fun getAllStudentRecordsFromServer(): List<StudentRecordDto> {
        return listOf()
    }

    private fun updateSyncStatusSharedPref(status: Boolean) {
        sharedPreferences.edit { putBoolean(SYNC_STATUS, status) }
    }

}
