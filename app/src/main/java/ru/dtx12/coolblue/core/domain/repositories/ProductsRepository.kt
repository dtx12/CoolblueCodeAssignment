package ru.dtx12.coolblue.core.domain.repositories

import ru.dtx12.coolblue.core.domain.models.ProductsPage


interface ProductsRepository {

    suspend fun findProducts(query: String, page: Int): ProductsPage
}