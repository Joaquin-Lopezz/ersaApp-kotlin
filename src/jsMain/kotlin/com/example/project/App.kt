// src/jsMain/kotlin/com/example/project/App.kt

package com.example.project
import io.kvision.Application
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapModule
import io.kvision.CoreModule
import io.kvision.html.Button
import io.kvision.html.Div
import io.kvision.html.Input
import io.kvision.html.InputType
import io.kvision.html.Label
import io.kvision.html.Span
import io.kvision.panel.root
import io.kvision.remote.registerRemoteTypes
import io.kvision.startApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import io.kvision.Hot
import kotlinx.browser.document






val AppScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        registerRemoteTypes()
        val rootDiv = root("kvapp") {}

        // Crear inputs con id
        val nameInput = Input(type = InputType.TEXT).apply { id = "nameInput" }
        val priceInput = Input(type = InputType.NUMBER).apply { id = "priceInput" }

        // Labels con atributo "for" para accesibilidad
        val nameLabel = Label("Nombre:")
        nameLabel.setAttribute("for", "nameInput")

        val priceLabel = Label("Precio:")
        priceLabel.setAttribute("for", "priceInput")

        val addButton = Button("Agregar producto").apply {
            id = "addButton"
        }
        val productListDiv = Div()

        // Agregar todos los elementos al rootDiv
        rootDiv.add(nameLabel)
        rootDiv.add(nameInput)
        rootDiv.add(priceLabel)
        rootDiv.add(priceInput)
        rootDiv.add(addButton)
        rootDiv.add(productListDiv)


        fun updateProductList() {
            AppScope.launch {
                val products = ProductModel.getProducts()
                productListDiv.removeAll()
                products.forEach { p ->
                    val productContainer = Div()
                    productContainer.addCssClass("product-item")

                    val productSpan = Span("ID: ${p.id}, Nombre: ${p.name}, Precio: ${p.price} ")
                    val deleteButton = Button("Eliminar").apply {
                        onClick {
                            AppScope.launch {
                                val success = ProductModel.deleteProduct(p.id)
                                if (success) {
                                    updateProductList()
                                } else {
                                    println("Error al eliminar producto con id ${p.id}")
                                }
                            }
                        }
                    }
                    productContainer.add(productSpan)
                    productContainer.add(deleteButton)

                    productListDiv.add(productContainer)
                }
            }
        }

        addButton.onClick { event ->
            event.preventDefault()
            val name = (document.getElementById("nameInput") as? org.w3c.dom.HTMLInputElement)?.value ?: ""
            val priceValue = (document.getElementById("priceInput") as? org.w3c.dom.HTMLInputElement)?.value ?: ""
            val price = priceValue.toDoubleOrNull() ?: 0.0


            if (name.isNotBlank() && price > 0) {
                AppScope.launch {
                    val success = ProductModel.addProduct(name, price)

                    if (success) {
                        nameInput.value = ""
                        priceInput.value = ""
                        updateProductList()
                    }
                }
            } else {
                println("Datos inválidos")
            }
        }

        // Mostrar lista inicialmente
        updateProductList()
    }
}

fun main() {
    startApplication(
        ::App,
        js("import.meta.webpackHot").unsafeCast<Hot?>(),
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}
