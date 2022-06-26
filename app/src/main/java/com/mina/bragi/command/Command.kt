package com.mina.bragi.command

import com.mina.bragi.data.CommandModel
import kotlinx.coroutines.delay

interface Command {
    // Receiver is passed at the execute() call, not sooner
    suspend fun execute()
}

class CommandImp(val model: CommandModel) : Command {
    override suspend fun execute() {
        model.performAction()
    }
}
