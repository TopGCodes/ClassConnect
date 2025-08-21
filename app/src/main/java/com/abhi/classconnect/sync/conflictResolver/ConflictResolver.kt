package com.abhi.classconnect.sync.conflictResolver

import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.dto.students.StudentRecordDto
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.local.entities.StudentRecord

interface ConflictResolver {
    fun resolveLessonConflict(remoteLesson : LessonDto, localLesson : Lesson): Lesson

    fun resolveStudentRecordConflict(remoteRecord : StudentRecordDto, localRecord : StudentRecord): StudentRecord

}