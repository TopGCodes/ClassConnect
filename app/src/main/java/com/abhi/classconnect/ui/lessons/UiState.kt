package com.abhi.classconnect.ui.lessons

import com.abhi.classconnect.ui.entities.LessonInfo

sealed class UiState{

    data object NoLessons : UiState()
    data class Success(val state : ScreenState) : UiState()
}


data class ScreenState(
    val lessons : List<LessonInfo>,
    val isOnline : String,
    val syncInfo : String
)