package com.abhi.classconnect.data.local.typeConvertor

import androidx.room.TypeConverter
import com.abhi.classconnect.data.models.AttendanceEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AttendanceEntryConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromAttendanceList(attendance: List<AttendanceEntry>?): String {
        return gson.toJson(attendance)
    }

    @TypeConverter
    fun toAttendanceList(data: String?): List<AttendanceEntry> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<AttendanceEntry>>() {}.type
        return gson.fromJson(data, listType)
    }
}