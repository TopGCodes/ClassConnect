package com.abhi.classconnect.ui.attendance

import com.abhi.classconnect.ui.entities.StudentRecordInfo

sealed class UiState {

    data object NoRecords : UiState()

    data class Success(val records: List<StudentRecordInfo>) : UiState()
}