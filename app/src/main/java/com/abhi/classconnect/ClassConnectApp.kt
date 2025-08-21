package com.abhi.classconnect

import android.app.Application
import com.abhi.classconnect.data.di.localDataSourceKoinModule
import com.abhi.classconnect.data.di.remoteDataSourceKoinModule
import com.abhi.classconnect.data.di.repositoryKoinModule
import com.abhi.classconnect.sync.di.syncKoinModule
import com.abhi.classconnect.ui.di.viewModelKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ClassConnectApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            this.androidContext(applicationContext)
            modules(
                viewModelKoinModule,
                repositoryKoinModule,
                remoteDataSourceKoinModule,
                localDataSourceKoinModule,
                syncKoinModule
            )
        }
    }
}