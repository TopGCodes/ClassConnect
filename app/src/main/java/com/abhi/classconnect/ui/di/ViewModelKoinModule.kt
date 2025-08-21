package com.abhi.classconnect.ui.di

import com.abhi.classconnect.ui.addlesson.AddLessonViewModel
import com.abhi.classconnect.ui.attendance.AttendanceViewModel
import com.abhi.classconnect.ui.lessons.LessonsViewModel
import org.koin.dsl.module

val  viewModelKoinModule  = module {

    factory { LessonsViewModel(get(),get(),get()) }
    factory { AddLessonViewModel(get()) }
    factory { AttendanceViewModel(get()) }

}