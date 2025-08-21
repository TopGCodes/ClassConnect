package com.abhi.classconnect.data.mappers

import com.abhi.classconnect.data.dto.lessons.AttachmentDto
import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.models.Attachment
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.ui.entities.LessonInfo


// This func will convert the room entity to normal object which can be used in UI without any unnecessary field
fun Lesson.toUiLesson() : LessonInfo{
    return LessonInfo(
        title = title,
        content = content,
        syncStatus = syncStatus.name,
        lastModified = lastModified,
        modifiedBy = modifiedBy
    )
}


// This func map Lesson entity to Dto Object, which will be passed to server
fun Lesson.toLessonDto() : LessonDto{
    return LessonDto(
        title = title,
        content = content,
        attachments = attachments.map { it.toAttachmentDto() },
        syncStatus = syncStatus,
        lastModified = lastModified,
        modifiedBy = modifiedBy
    )
}


fun LessonDto.toLessonEntity() : Lesson{
    return Lesson(
        title = title,
        content = content,
        attachments = attachments.map { it.toAttachmentEntity() },
        syncStatus = syncStatus,
        lastModified = lastModified,
        modifiedBy = modifiedBy
    )
}

fun Attachment.toAttachmentDto() : AttachmentDto{
    return AttachmentDto(
        this.uri
    )
}

fun AttachmentDto.toAttachmentEntity() : Attachment{
    return Attachment(
        this.uri
    )
}
