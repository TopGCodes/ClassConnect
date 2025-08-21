package com.abhi.classconnect.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi.classconnect.data.models.AttendanceEntry
import com.abhi.classconnect.data.models.ScoreEntry
import com.abhi.classconnect.data.models.SyncStatus
import com.abhi.classconnect.utils.STUDENT_RECORD_TABLE
import com.abhi.classconnect.utils.currentTimeInMillis
import java.util.UUID

@Entity(tableName = STUDENT_RECORD_TABLE)
data class StudentRecord(
    val id: String = UUID.randomUUID().toString(),
    @PrimaryKey
    val name: String,
    val attendance: List<AttendanceEntry>,
    val scores: List<ScoreEntry>,
    val syncStatus: SyncStatus,
    val lastModifiedBy: Long = currentTimeInMillis()
)