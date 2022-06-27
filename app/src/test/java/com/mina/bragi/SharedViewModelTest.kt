package com.mina.bragi

import com.mina.bragi.data.ConnectionOptions
import com.mina.bragi.data.ConnectionRepository
import com.mina.bragi.data.assertNoCompleteOrErrors
import com.mina.bragi.intent.SharedIntent
import com.mina.bragi.state.ConnectionState
import com.mina.movieslist.effects.SharedEffects
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.TestScheduler
import kotlinx.coroutines.Dispatchers
import org.hamcrest.core.AnyOf
import org.junit.Before
import org.junit.Test


class SharedViewModelTest {

    @MockK
    lateinit var repository: ConnectionRepository

    private lateinit var vm: SharedViewModel

    @Before
    fun setup() {

        MockKAnnotations.init(this)
        //setup
        coEvery {
            repository.startConnectionObserving()
        } returns Observable.create {
            it.onNext(ConnectionState(ConnectionOptions.CONNECTION_ERROR))
        }

        vm = SharedViewModel(repository, TestScheduler())
    }


    @Test
    fun `test when send StartConnectionInterval intent it should change the state to the new connection state`() {
        vm.sendAction(SharedIntent.StartConnectionInterval)
        val testObserverState = vm.state.test()

        testObserverState.assertValue(ConnectionState(ConnectionOptions.CONNECTION_ERROR))

        testObserverState.assertNoCompleteOrErrors()
    }


}