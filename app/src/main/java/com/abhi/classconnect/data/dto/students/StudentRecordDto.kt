package com.abhi.classconnect.data.dto.students

import com.abhi.classconnect.data.models.SyncStatus
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class StudentRecordDto(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val attendance: List<AttendanceEntryDto>,
    val scores: List<ScoreEntryDto>,
    val syncStatus: SyncStatus,
    val lastModified : Long
)