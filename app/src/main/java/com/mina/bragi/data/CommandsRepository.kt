package com.mina.bragi.data

import android.util.Log
import com.google.android.material.appbar.AppBarLayout
import com.mina.bragi.state.ConnectionState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

import java.util.concurrent.TimeUnit

interface ICommandsRepository {
    fun getCommandsObservable(): Observable<ConnectionState>
}

//class CommandsRepository() : ICommandsRepository {
//
//    override fun getCommandsObservable(): Observable<ConnectionState> {
//        val commands = BehaviorSubject.create()
//        commands.observeOn(
//            AndroidSchedulers.mainThread()
//        )
//    }
//}