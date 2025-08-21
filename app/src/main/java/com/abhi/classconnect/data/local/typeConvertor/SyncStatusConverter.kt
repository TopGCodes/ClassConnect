package com.abhi.classconnect.data.local.typeConvertor

import androidx.room.TypeConverter
import com.abhi.classconnect.data.models.SyncStatus

class SyncStatusConverter {

    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String {
        return status.name // Store enum as a String
    }

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus {
        return SyncStatus.valueOf(value)
    }
}