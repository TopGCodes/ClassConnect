package com.abhi.classconnect.sync.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.abhi.classconnect.sync.InternetConnectivityObserver
import com.abhi.classconnect.sync.SyncEngine
import com.abhi.classconnect.sync.SyncWorker
import com.abhi.classconnect.sync.conflictResolver.ConflictResolver
import com.abhi.classconnect.sync.conflictResolver.ConflictResolverImpl
import org.koin.dsl.module

val syncKoinModule = module {


    factory<NetworkRequest> {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    single<ConnectivityManager> {
        val context = get<Context>()
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }


    single { InternetConnectivityObserver(get()) }


    single<WorkManager> {
        val context = get<Context>()
        WorkManager.getInstance(context)
    }

    single<WorkRequest> {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // only run when internet is on
            .build()

        OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()
    }


    single {
        SyncEngine(get(), get(), get(), get(), get(), get())
    }

    single<ConflictResolver> {
        ConflictResolverImpl()
    }




}