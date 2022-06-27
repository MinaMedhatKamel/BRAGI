package com.mina.bragi.command

import com.mina.bragi.data.CommandModel

interface Command {
    suspend fun execute()
}

class CommandImp(val model: CommandModel) : Command {
    override suspend fun execute() {
        model.performAction()
    }
}
