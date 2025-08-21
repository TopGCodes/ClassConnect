package com.abhi.classconnect.data.di

import com.abhi.classconnect.data.repository.AttendanceRepository
import com.abhi.classconnect.data.repository.LessonsRepository
import com.abhi.classconnect.data.repository.SyncStatusRepository
import com.abhi.classconnect.data.repository.repositoryImpl.AttendanceRepositoryImpl
import com.abhi.classconnect.data.repository.repositoryImpl.LessonsRepositoryImpl
import com.abhi.classconnect.data.repository.repositoryImpl.SyncStatusRepositoryImpl
import org.koin.dsl.module

val  repositoryKoinModule  = module{
   factory<LessonsRepository> { LessonsRepositoryImpl(get(), get(),get() , get()) }
   factory<AttendanceRepository> { AttendanceRepositoryImpl(get(),get(),get(),get()) }
   factory<SyncStatusRepository> { SyncStatusRepositoryImpl(get()) }
}
