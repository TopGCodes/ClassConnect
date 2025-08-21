package com.abhi.classconnect.sync

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    appContext: Context,
    workerParameters: WorkerParameters,
    private val syncEngine: SyncEngine
) : CoroutineWorker(appContext, workerParameters) {

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        return try {
            syncEngine.performSync()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }

    }
}