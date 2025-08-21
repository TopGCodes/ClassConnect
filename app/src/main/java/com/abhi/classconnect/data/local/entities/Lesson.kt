package com.abhi.classconnect.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi.classconnect.data.models.Attachment
import com.abhi.classconnect.data.models.SyncStatus
import com.abhi.classconnect.utils.LESSON_TABLE
import com.abhi.classconnect.utils.currentTimeInMillis
import java.util.UUID

@Entity(tableName = LESSON_TABLE)
data class Lesson (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val attachments: List<Attachment>,
    val syncStatus: SyncStatus,
    val lastModified: Long = currentTimeInMillis(),
    val modifiedBy: String
)
