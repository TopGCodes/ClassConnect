package com.abhi.classconnect.data.dto.students

import kotlinx.serialization.Serializable

@Serializable
data class ScoreEntryDto(
    val subject: String,
    val marks: Int
)