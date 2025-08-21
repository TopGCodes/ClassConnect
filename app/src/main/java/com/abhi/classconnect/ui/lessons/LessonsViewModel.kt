package com.abhi.classconnect.ui.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.classconnect.data.repository.LessonsRepository
import com.abhi.classconnect.data.repository.SyncStatusRepository
import com.abhi.classconnect.sync.SyncEngine
import com.abhi.classconnect.utils.Offline
import com.abhi.classconnect.utils.Online
import com.abhi.classconnect.utils.PENDING
import com.abhi.classconnect.utils.SYNCED
import com.abhi.classconnect.utils.extensions.whileUiSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class LessonsViewModel(
  private val lessonsRepository: LessonsRepository,
  private val syncStatusRepository: SyncStatusRepository,
   private val syncEngine: SyncEngine
) : ViewModel() {


    init {
        viewModelScope.launch {
            syncEngine.enqueueWorkRequest()
        }
    }

     val uiState: StateFlow<UiState> = combine(
        lessonsRepository.observeAllLessons(),
        lessonsRepository.getNetworkStatus(),
         syncStatusRepository.observeSyncStatus()
    ) { lessons, networkStatus,syncStatusCompleted ->
        if (lessons.isNotEmpty()) {
            UiState.Success(
                state = ScreenState(
                    lessons = lessons,
                    isOnline = if(networkStatus) Online else Offline,
                    syncInfo = if(syncStatusCompleted) SYNCED else PENDING
                )
            )
        } else {
            UiState.NoLessons
        }
    }.whileUiSubscribed(viewModelScope, UiState.NoLessons)


    fun initiateSync() {
        viewModelScope.launch {
            syncEngine.performSync()
        }
    }
}