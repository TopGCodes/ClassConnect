package com.abhi.classconnect.ui.entities

// Following the architecture strictly , this class is created for UI , what to be displayed instead all unnecessary fields

data class LessonInfo(
    val title : String,
    val content : String,
    val syncStatus: String,
    val lastModified: Long,
    val modifiedBy: String
)