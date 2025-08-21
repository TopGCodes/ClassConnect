package com.abhi.classconnect.ui.entities

import com.abhi.classconnect.data.models.AttendanceEntry
import com.abhi.classconnect.data.models.ScoreEntry
import com.abhi.classconnect.data.models.SyncStatus

data class StudentRecordInfo(
    val name: String,
    val attendance: List<AttendanceEntry>,
    val scores: List<ScoreEntry>,
    val syncStatus: SyncStatus
)