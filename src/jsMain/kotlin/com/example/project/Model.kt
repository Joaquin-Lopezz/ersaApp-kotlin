// src/jsMain/kotlin/com/example/project/Model.kt
package com.example.project

import dev.kilua.rpc.getService

object Model {

    private val pingService = getService<IPingService>()

    suspend fun ping(message: String): String {
        return pingService.ping(message)
    }

}
