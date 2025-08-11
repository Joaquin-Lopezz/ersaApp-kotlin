// src/commonMain/kotlin/com/example/project/Product.kt
package com.example.project

import kotlinx.serialization.Serializable

@Serializable
data class Product(val id: Int, val name: String, val price: Double)
