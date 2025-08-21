package com.abhi.classconnect.data.di

import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.abhi.classconnect.data.local.ClassroomDAO
import com.abhi.classconnect.data.local.ClassroomDatabase
import com.abhi.classconnect.data.local.sharedPref.SharedPrefLocalDataSource
import com.abhi.classconnect.data.local.sharedPref.SharedPrefLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val  localDataSourceKoinModule  = module {

    single<ClassroomDatabase> {
        Room.databaseBuilder(
            context = androidContext().applicationContext,
            ClassroomDatabase::class.java,
            "Classroom_database"
        ).build()
    }

    single<ClassroomDAO> { get<ClassroomDatabase>().classRoomDao() }


    single {
        val context = get<Context>().applicationContext
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

          EncryptedSharedPreferences.create(
            context,
            "secret_pref",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<SharedPrefLocalDataSource> { SharedPrefLocalDataSourceImpl(get()) }

}
