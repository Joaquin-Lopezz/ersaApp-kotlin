// src/jvmMain/kotlin/com/example/project/Main.kt
package com.example.project

import dev.kilua.rpc.applyRoutes
import dev.kilua.rpc.getAllServiceManagers
import dev.kilua.rpc.initRpc
import dev.kilua.rpc.registerService
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.kvision.remote.registerRemoteTypes


val productServiceInstance = ProductService()

fun Application.main() {
    println("🚀 Servidor iniciado")
    registerRemoteTypes()
    install(Compression)
    install(WebSockets)
    routing {
        getAllServiceManagers().forEach { applyRoutes(it) }
    }
    initRpc {
        registerService<IPingService> { PingService() }
        registerService<IProductService> { productServiceInstance }
    }
}
