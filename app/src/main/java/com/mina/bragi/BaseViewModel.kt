package com.mina.bragi

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel<S, I, E>(initState: S) : ViewModel() {

    private val _intent: PublishSubject<I> = PublishSubject.create()
    val intent: PublishSubject<I> = _intent

    private val _effects: PublishSubject<E> = PublishSubject.create()
    val effects: PublishSubject<E> = _effects

    private val _state: BehaviorSubject<S> = BehaviorSubject.createDefault(initState)
    val state: BehaviorSubject<S> = _state

    init {
        observeIntents()
    }

    private fun observeIntents() {
        intent.subscribe { processIntents(it) }
    }

    fun sendAction(action: I) {
        _intent.onNext(action)
    }

    fun sendEffect(effect: E) {
        _effects.onNext(effect)
    }

    fun emitState(state: S) {
        _state.onNext(state)
    }

    abstract fun processIntents(intent: I)


}