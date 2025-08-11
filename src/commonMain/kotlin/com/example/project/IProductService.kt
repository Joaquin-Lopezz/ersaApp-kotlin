// src/commonMain/kotlin/com/example/project/IProductService.kt
package com.example.project

import dev.kilua.rpc.annotations.RpcService

@RpcService
interface  IProductService {
    suspend fun addProduct(product: Product): Boolean
    suspend fun getProducts(): List<Product>
    suspend fun deleteProduct(id: Int): Boolean
}
