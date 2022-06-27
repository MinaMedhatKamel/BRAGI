package com.mina.bragi

import com.mina.bragi.command.Command
import com.mina.bragi.command.CommandImp
import com.mina.bragi.command.CommandProcessor
import com.mina.bragi.data.CommandModel
import com.mina.bragi.data.ConnectionOptions
import com.mina.bragi.data.ConnectionRepository
import com.mina.bragi.intent.SharedIntent
import com.mina.bragi.state.ConnectionState
import com.mina.movieslist.effects.SharedEffects
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

class SharedViewModel(
    private val repo: ConnectionRepository = ConnectionRepository(),
    val schedulers: Scheduler = AndroidSchedulers.mainThread()
) :
    BaseViewModel<ConnectionState, SharedIntent, SharedEffects>(
        ConnectionState(ConnectionOptions.CONNECTION_ERROR)
    ) {
    private var disposable: Disposable? = null

    init {
        sendAction(SharedIntent.StartConnectionInterval)
    }

    private fun startConnection() {
        disposable = repo.startConnectionObserving()
            .observeOn(schedulers)
            .distinctUntilChanged().subscribe { newState ->
                emitState(newState)
                sendEffect(SharedEffects.ShowPop(newState.newConnectionState.name))
            }
    }

    override fun processIntents(intent: SharedIntent) {
        when (intent) {
            SharedIntent.StartConnectionInterval -> startConnection()
            SharedIntent.stopConnectionObserving -> disposeConnectionSubscribtion()
            SharedIntent.ConnectionCheckClick -> checkConnectionState()
            SharedIntent.NavigateToSignUp -> navigateToSignUp()
            SharedIntent.NavigateToForgetPass -> navigateToForgetPass()
            SharedIntent.SendCommands -> sendCommands()
        }
    }

    private fun disposeConnectionSubscribtion() {
        disposable?.dispose()
    }

    private fun checkConnectionState() {
        state.value?.let {
            if (it.newConnectionState == ConnectionOptions.CONNECTION_ESTABLISHED) {
                sendEffect(SharedEffects.ShowPop("message sent " + it.newConnectionState.name))
            }
        }
    }

    private fun sendCommands() {
        val listOfCommands = mutableListOf<Command>()
        for (i in 1..10) {
            listOfCommands.add(CommandImp(CommandModel(i, "my name is $i")))
        }
        CommandProcessor.processList(listOfCommands)
    }

    private fun navigateToForgetPass() {
        sendEffect(SharedEffects.NavigateToForgetPass)
    }

    private fun navigateToSignUp() {
        sendEffect(SharedEffects.NavigateToSignUp)
    }
}
