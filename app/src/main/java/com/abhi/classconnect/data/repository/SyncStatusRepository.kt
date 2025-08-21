package com.abhi.classconnect.data.repository

import kotlinx.coroutines.flow.Flow

interface SyncStatusRepository {

    fun observeSyncStatus(): Flow<Boolean>
}