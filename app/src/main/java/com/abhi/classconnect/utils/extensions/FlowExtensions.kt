package com.abhi.classconnect.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun<T> Flow<T>.whileUiSubscribed(viewModelScope: CoroutineScope, initialValue : T): StateFlow<T>{
  return this.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = initialValue
  )
}