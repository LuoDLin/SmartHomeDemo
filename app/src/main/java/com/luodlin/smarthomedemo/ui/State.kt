package com.luodlin.smarthomedemo.ui

sealed interface State {
    object None : State
    object Loading : State
    object Success : State
    object Failed : State
}