package com.mina.bragi.intent

sealed class SharedIntent {
    object StartConnectionInterval : SharedIntent()
    object ApplyEstablishedConnectionFilter : SharedIntent()
    object ResetEstablishedConnectionFilter : SharedIntent()
    object NavigateToSignUp : SharedIntent()
    object NavigateToForgetPass : SharedIntent()
    object SendCommands : SharedIntent()
}