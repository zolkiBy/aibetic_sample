package com.example.aibetictest.presentation.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseReducer<S : UiState, E : Event>(initialValue: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialValue)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        _state.tryEmit(newState)
    }

    abstract fun reduce(oldState: S, event: E)
}

interface UiState

interface Event
