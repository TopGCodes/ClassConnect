package com.abhi.classconnect.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.data.models.SyncStatus
import kotlinx.coroutines.flow.Flow


@Dao
interface ClassroomDAO{

    @Query("SELECT * FROM lessons_table")
    fun observeAllLessons() : Flow<List<Lesson>>
    @Query("SELECT * FROM lessons_table WHERE syncStatus IS :status")
    fun getPendingSyncLessons(status : SyncStatus = SyncStatus.PENDING) : List<Lesson>
    @Query("SELECT * FROM lessons_table WHERE id Is :id")
    suspend fun getLessonById(id : String) : Lesson?
    @Upsert
    suspend fun saveLessonInLocal(lesson : Lesson)

    @Update
    suspend fun updateLessonSyncStatusInLocal(lesson: Lesson)



    // Students
    @Query("SELECT * FROM student_record")
    fun observeStudentsRecords() : Flow<List<StudentRecord>>

    @Query("SELECT * FROM student_record WHERE syncStatus IS :status")
    fun getPendingSyncStudentsRecords(status : SyncStatus = SyncStatus.PENDING) : List<StudentRecord>
    @Upsert
    suspend fun saveStudentRecordInLocal(record : StudentRecord)

    @Query("SELECT * FROM student_record WHERE id Is :id")
    suspend fun getStudentRecordById(id : String) : StudentRecord?

    @Update
    suspend fun updateStudentSyncStatusInLocal(record: StudentRecord)





}