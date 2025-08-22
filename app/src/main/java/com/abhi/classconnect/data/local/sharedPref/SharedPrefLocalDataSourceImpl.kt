package com.abhi.classconnect.data.local.sharedPref

import android.content.SharedPreferences
import com.abhi.classconnect.utils.SYNC_STATUS
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SharedPrefLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPrefLocalDataSource {

    override fun observeSyncStatus(): Flow<Boolean> = callbackFlow {
        trySend(false)
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
                if (key.toString() == SYNC_STATUS) {
                    val newValue = prefs.getBoolean(key, false)
                    trySend(newValue)
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }
}