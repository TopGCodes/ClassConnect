package com.abhi.classconnect.data.mappers

import com.abhi.classconnect.data.dto.students.AttendanceEntryDto
import com.abhi.classconnect.data.dto.students.ScoreEntryDto
import com.abhi.classconnect.data.dto.students.StudentRecordDto
import com.abhi.classconnect.data.models.AttendanceEntry
import com.abhi.classconnect.data.models.ScoreEntry
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.ui.entities.StudentRecordInfo

fun StudentRecord.toStudentUi() : StudentRecordInfo{
    return StudentRecordInfo(
        name = name,
        attendance = attendance,
        scores = scores,
        syncStatus = syncStatus
    )
}


fun StudentRecord.toStudentRecordDto() : StudentRecordDto{
    return StudentRecordDto(
        name = name,
        attendance = attendance.map { it.toAttendanceEntryDto() },
        scores = scores.map { it.toScoreEntryDto() },
        syncStatus = syncStatus,
        lastModified = lastModifiedBy
    )
}


fun StudentRecordDto.toStudentRecordEntity() : StudentRecord{
    return StudentRecord(
        name = name,
        attendance = attendance.map { it.toAttendanceEntryEntity() },
        scores = scores.map { it.toScoreEntryEntity() },
        syncStatus = syncStatus,
        lastModifiedBy = lastModified
    )
}




fun AttendanceEntry.toAttendanceEntryDto() : AttendanceEntryDto{
    return AttendanceEntryDto(
        date = date,
        isPresent = isPresent
    )
}

fun ScoreEntry.toScoreEntryDto() : ScoreEntryDto{
    return ScoreEntryDto(
        subject = subject,
        marks = marks
    )
}


fun AttendanceEntryDto.toAttendanceEntryEntity() : AttendanceEntry{
    return AttendanceEntry(
        date = date,
        isPresent = isPresent
    )
}

fun ScoreEntryDto.toScoreEntryEntity() : ScoreEntry{
    return ScoreEntry(
        subject = subject,
        marks = marks
    )
}




