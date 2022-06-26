package com.mina.movieslist.effects

sealed class SharedEffects {
    object NavigateToSignUp : SharedEffects()
    object NavigateToForgetPass : SharedEffects()
}