package com.mina.bragi.data

import kotlinx.coroutines.delay

data class CommandModel(val id: Int, val name: String) {
    suspend fun performAction() {
        println("CommandModel performing action in Command #$id name #$name")
        delay(id * 1000L)
        println("CommandModel action finished! in $id seconds")
    }
    //this method for testing rx java step 2 solution.
     fun performActionSync() {
        println("CommandModel performing action in Command #$id name #$name")
    }
}
