// src/jvmMain/kotlin/com/example/project/ProductService.kt
package com.example.project

class ProductService : IProductService {

    private val products = mutableListOf<Product>()
    private var currentId = 1

    override suspend fun addProduct(product: Product): Boolean {
        val productWithId = product.copy(id = currentId++)
        products.add(productWithId)
        println("📦 Producto agregado: $productWithId")
        println("📋 Lista actual: $products")
        return true
    }

    override suspend fun getProducts(): List<Product> {
        println("🔍 getProducts() llamado, productos actuales: $products")
        return products.toList()
    }

    override suspend fun deleteProduct(id: Int): Boolean {
        val removed = products.removeIf { it.id == id }
        println("🗑️ Producto con id $id eliminado: $removed")
        println("📋 Lista actual después de eliminar: $products")
        return removed
    }
}
