package com.abhi.classconnect.ui.addlesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.classconnect.data.local.entities.Lesson
import com.abhi.classconnect.data.repository.LessonsRepository
import kotlinx.coroutines.launch

class AddLessonViewModel(
    private val lessonsRepository: LessonsRepository
) : ViewModel() {


    fun saveLesson(lesson: Lesson){
        viewModelScope.launch {
            lessonsRepository.saveLessonInLocal(lesson)
        }
    }
}