package com.mina.movieslist.effects

sealed class SharedEffects {
    object NavigateToSignUp : SharedEffects()
    object NavigateToForgetPass : SharedEffects()
    data class ShowPop(val message: String) : SharedEffects()
}