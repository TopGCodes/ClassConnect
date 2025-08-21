package com.abhi.classconnect.sync.conflictResolver

import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.dto.students.StudentRecordDto
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.data.mappers.toLessonEntity
import com.abhi.classconnect.data.mappers.toStudentRecordEntity

class ConflictResolverImpl : ConflictResolver {
    override fun resolveLessonConflict(
        remoteLesson: LessonDto,
        localLesson: Lesson
    ) : Lesson {
      return if(remoteLesson.lastModified >= localLesson.lastModified){
             remoteLesson.toLessonEntity()
        }
        else{
            localLesson
        }
    }

    override fun resolveStudentRecordConflict(
        remoteRecord: StudentRecordDto,
        localRecord: StudentRecord
    ): StudentRecord {
        return if(remoteRecord.lastModified >= localRecord.lastModifiedBy){
            remoteRecord.toStudentRecordEntity()
        }
        else localRecord
    }
}