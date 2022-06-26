package com.mina.bragi.intent

sealed class SharedIntent {
    object StartConnectionInterval : SharedIntent()
    object stopConnectionObserving : SharedIntent()
    object ConnectionCheckClick : SharedIntent()
    object NavigateToSignUp : SharedIntent()
    object NavigateToForgetPass : SharedIntent()
    object SendCommands : SharedIntent()
}