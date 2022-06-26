package com.mina.bragi.command

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

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