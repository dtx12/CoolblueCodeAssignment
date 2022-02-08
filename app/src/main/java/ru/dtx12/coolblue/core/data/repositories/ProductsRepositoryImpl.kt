package ru.dtx12.coolblue.core.data.repositories

import ru.dtx12.coolblue.core.data.endpoints.SearchApi
import ru.dtx12.coolblue.core.data.toProductPage
import ru.dtx12.coolblue.core.domain.models.ProductsPage
import ru.dtx12.coolblue.core.domain.repositories.ProductsRepository
import ru.dtx12.coolblue.core.exceptions.NoInternetException
import ru.dtx12.coolblue.core.platform.NetworkHandler
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val dataSource: SearchApi,
    private val networkHandler: NetworkHandler
) : ProductsRepository {
    override suspend fun findProducts(query: String, page: Int): ProductsPage {
        if (query.isBlank()) {
            return ProductsPage(listOf(), 1, 0, 0, 1)
        }
        if (networkHandler.isNetworkAvailable()) {
            return dataSource.searchForProducts(query, page).toProductPage()
        } else {
            throw NoInternetException()
        }
    }
}