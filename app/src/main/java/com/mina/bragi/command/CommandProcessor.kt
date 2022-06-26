package com.mina.bragi.command

import com.mina.bragi.data.CommandModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object CommandProcessor {
    private val commandsChannel = Channel<Command>()

    // using separate Scopes for adding and executing commands solves blocking one by another
    private val processScope =
        CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
    private val executeScope =
        CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())

    fun processList(commands: List<Command>) {
        processScope.launch {
            commands.forEach {
                println("CommandProcessor adding $it to the queue")
                commandsChannel.send(it)
            }

        }
    }

    init {
        // waiting for new commands in the queue and executing them as soon as they come
        executeScope.launch {
            for (command in commandsChannel) {
                command.execute()
            }
        }
    }
}

//Step 2 using RXjAVA
fun main() {
    Observable.create<CommandModel> {
        for (i in 1..10) {
            val command = CommandModel(i, "my name is $i")
            it.onNext(command)
        }
    }.concatMap { s ->
        Observable.interval(s.id.toLong(), TimeUnit.SECONDS).take(1).map { s }
    }
        .subscribe {
            it.performActionSync()
            println("CommandModel action finished! in ${it.id} seconds")
        }

    // for testing only on the main function
    Thread.sleep(60000)
}