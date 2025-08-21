package com.abhi.classconnect.data.repository.repositoryImpl

import com.abhi.classconnect.data.local.sharedPref.SharedPrefLocalDataSource
import com.abhi.classconnect.data.repository.SyncStatusRepository
import kotlinx.coroutines.flow.Flow

class SyncStatusRepositoryImpl(
    private val sharedPrefLocalDataSource: SharedPrefLocalDataSource
) : SyncStatusRepository {
    override fun observeSyncStatus(): Flow<Boolean> {
        return sharedPrefLocalDataSource.observeSyncStatus()
    }
}