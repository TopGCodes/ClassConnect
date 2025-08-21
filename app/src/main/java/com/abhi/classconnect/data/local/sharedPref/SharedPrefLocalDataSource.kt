package com.abhi.classconnect.data.local.sharedPref

import kotlinx.coroutines.flow.Flow

interface SharedPrefLocalDataSource {

    fun observeSyncStatus() : Flow<Boolean>
}