package com.abhi.classconnect.data.repository

import com.abhi.classconnect.data.dto.students.StudentRecordDto
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.ui.entities.StudentRecordInfo
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    // Local Db Methods
    fun observeStudentsRecords() : Flow<List<StudentRecordInfo>>
    fun getPendingSyncStudentsRecords() : List<StudentRecordDto>
    suspend fun saveStudentRecordInLocal(record : StudentRecord)
    suspend fun getStudentRecordFromLocal(id : String) : StudentRecord?

    suspend fun updateStudentSyncStatusInLocal(studentRecord: StudentRecord)


    // Server Methods
    suspend fun getAllStudentRecordsFromServer() : List<StudentRecordDto>
    suspend fun uploadStudentRecordToServer(record: StudentRecordDto) : Boolean

}