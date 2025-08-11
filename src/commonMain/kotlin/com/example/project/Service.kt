// src/commonMain/kotlin/com/example/project/Service.kt
package com.example.project

import dev.kilua.rpc.annotations.RpcService

@RpcService
interface IPingService {
    suspend fun ping(message: String): String
}
