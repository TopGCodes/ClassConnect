package com.abhi.classconnect.data.local.typeConvertor

import androidx.room.TypeConverter
import com.abhi.classconnect.data.models.ScoreEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScoreEntryConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromScoreList(scores: List<ScoreEntry>?): String {
        return gson.toJson(scores)
    }

    @TypeConverter
    fun toScoreList(data: String?): List<ScoreEntry> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<ScoreEntry>>() {}.type
        return gson.fromJson(data, listType)
    }
}
