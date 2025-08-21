package com.abhi.classconnect.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi.classconnect.data.local.typeConvertor.AttachmentConverter
import com.abhi.classconnect.data.local.typeConvertor.AttendanceEntryConverter
import com.abhi.classconnect.data.local.typeConvertor.ScoreEntryConverter
import com.abhi.classconnect.data.local.typeConvertor.SyncStatusConverter
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.local.entities.StudentRecord

@Database(entities = [Lesson::class, StudentRecord::class],  version = 2, exportSchema = false)
@TypeConverters(value = [AttachmentConverter::class, AttendanceEntryConverter::class, ScoreEntryConverter::class, SyncStatusConverter::class])
abstract class ClassroomDatabase : RoomDatabase() {

    abstract fun classRoomDao() : ClassroomDAO

}