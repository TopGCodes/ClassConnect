package com.abhi.classconnect.data.local.typeConvertor

import androidx.room.TypeConverter
import com.abhi.classconnect.data.models.Attachment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AttachmentConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromAttachmentList(attachments: List<Attachment>?): String {
        return gson.toJson(attachments)
    }

    @TypeConverter
    fun toAttachmentList(data: String?): List<Attachment> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Attachment>>() {}.type
        return gson.fromJson(data, listType)
    }
}