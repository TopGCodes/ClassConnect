package com.abhi.classconnect.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class SyncStatus {
    SYNCED,
    PENDING,
    FAILED
}