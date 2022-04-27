package com.example.aibetictest.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<S : UiState, E : Event, out R : BaseReducer<S, E>> : ViewModel() {
    abstract val state: Flow<S>
    abstract val reducer: R

    fun sendEvent(event: E) {
        reducer.sendEvent(event)
    }
}