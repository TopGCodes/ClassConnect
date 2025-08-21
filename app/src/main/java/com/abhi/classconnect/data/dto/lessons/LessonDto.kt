package com.abhi.classconnect.data.dto.lessons

import com.abhi.classconnect.data.models.SyncStatus
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class LessonDto(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val attachments: List<AttachmentDto>,
    val syncStatus: SyncStatus,
    val lastModified: Long,
    val modifiedBy: String
)