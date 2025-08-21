package com.abhi.classconnect.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.classconnect.data.local.entities.StudentRecord
import com.abhi.classconnect.data.repository.AttendanceRepository
import com.abhi.classconnect.utils.extensions.whileUiSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AttendanceViewModel(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    val uiState: StateFlow<UiState> = getAllStudentsRecords()
        .whileUiSubscribed(
            viewModelScope,
            UiState.NoRecords
        )


    fun getAllStudentsRecords() = flow {
        val studentsRecords = attendanceRepository.observeStudentsRecords()
        studentsRecords.collect { list ->
            if (list.isNotEmpty()) {
                emit(UiState.Success(list))
            }
            else{
                emit(UiState.NoRecords)
            }
        }
    }

    fun saveStudentRecord(record: StudentRecord){
        viewModelScope.launch {
            attendanceRepository.saveStudentRecordInLocal(record)
        }
    }
}