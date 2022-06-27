package com.mina.bragi.data

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test

class ConnectionRepositoryTest {
    lateinit var repository: ConnectionRepository

    @Before
    fun setUp() {
        val testScheduler = TestScheduler()
        repository = ConnectionRepository(testScheduler)
    }

    @Test
    fun `test connection observer returns values without errors`() {
        // When
        val testObserver = repository.startConnectionObserving().test()

        // Then
        testObserver.assertNoCompleteOrErrors()
    }
}

fun <T> TestObserver<T>.assertNoCompleteOrErrors() {
    assertNoErrors()
    assertNotComplete()
}