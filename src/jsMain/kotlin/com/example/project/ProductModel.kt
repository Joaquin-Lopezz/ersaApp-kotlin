// src/jsMain/kotlin/com/example/project/ProductModel.kt
package com.example.project

import dev.kilua.rpc.getService

object ProductModel {
    private val productService = getService<IProductService>()

    suspend fun addProduct(name: String, price: Double): Boolean {
        val product = Product(0, name, price)
        return productService.addProduct(product)
    }

    suspend fun getProducts(): List<Product> {

        return productService.getProducts()
    }


    suspend fun deleteProduct(id: Int): Boolean {
        return productService.deleteProduct(id)
    }
}
