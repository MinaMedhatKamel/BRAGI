package com.mina.bragi

import android.util.Log
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
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class SharedViewModel(private val repo: ConnectionRepository = ConnectionRepository()) :
    BaseViewModel<ConnectionState, SharedIntent, SharedEffects>(
        ConnectionState(ConnectionOptions.CONNECTION_ERROR)
    ) {

    private var isEstablishedConnectionEnabled = false;

    init {
        sendAction(SharedIntent.StartConnectionInterval)
    }

    private fun startConnection() {
        repo.startConnectionObserving().observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged().subscribe { newState ->
                emitState(newState)

//                var disposable: Disposable? = null
//                val consumer = object : (ConnectionState) -> Unit {
//                    override fun invoke(previous: ConnectionState) {
//                        Log.d(
//                            "connection",
//                            "startConnection: new ${newState.newConnectionState.name} old ${previous.newConnectionState.name}"
//                        )
//                        if (!newState.equals(previous)) {
//                            emitState(newState)
//                            disposable?.dispose()
//                            disposable = null
//                        }
//                    }
//                }
//
//                if (isEstablishedConnectionEnabled) {
//                    // emit only the connection established state.
//                    if (newState.newConnectionState.equals(ConnectionOptions.CONNECTION_ESTABLISHED))
//                        emitState(newState)
//                } else {
//                    //subscription for checking the last state and compare it with the newone.
//                    disposable =
//                        state.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)
//                }
//            }
            }
    }

    override fun processIntents(intent: SharedIntent) {
        when (intent) {
            SharedIntent.StartConnectionInterval -> startConnection()
            SharedIntent.ApplyEstablishedConnectionFilter -> applyEstablishedConnectionFilter()
            SharedIntent.ResetEstablishedConnectionFilter -> resetEstablishedConnectionFilter()
            SharedIntent.NavigateToSignUp -> navigateToSignUp()
            SharedIntent.NavigateToForgetPass -> navigateToForgetPass()
            SharedIntent.SendCommands -> sendCommands()
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

    private fun applyEstablishedConnectionFilter() {
        isEstablishedConnectionEnabled = true
    }

    private fun resetEstablishedConnectionFilter() {
        isEstablishedConnectionEnabled = false
    }
}
