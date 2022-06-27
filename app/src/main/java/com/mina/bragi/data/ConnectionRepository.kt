package com.mina.bragi.data

import android.util.Log
import com.mina.bragi.state.ConnectionState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

import java.util.concurrent.TimeUnit

interface IConnectionRepository {
    fun startConnectionObserving(): Observable<ConnectionState>
}

class ConnectionRepository(val schedulers: Scheduler = AndroidSchedulers.mainThread()) : IConnectionRepository {

    override fun startConnectionObserving(): Observable<ConnectionState> {
        return Observable.interval(1L, TimeUnit.SECONDS).observeOn(
            schedulers
        ).map {
            val value = ConnectionOptions.values().random()
            Log.d("connection", "startConnectionObserving: $value")
            ConnectionState(value)
        }
    }
}
