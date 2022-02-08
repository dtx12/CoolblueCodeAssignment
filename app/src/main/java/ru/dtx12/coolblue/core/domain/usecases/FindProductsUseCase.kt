package ru.dtx12.coolblue.core.domain.usecases

import ru.dtx12.coolblue.core.domain.models.ProductsPage
import ru.dtx12.coolblue.core.domain.repositories.ProductsRepository
import ru.dtx12.coolblue.core.interactor.UseCase
import javax.inject.Inject

class FindProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) : UseCase<ProductsPage, FindProductsUseCaseParameters>() {
    override suspend fun run(params: FindProductsUseCaseParameters): ProductsPage {
        return repository.findProducts(params.query, params.page)
    }
}

data class FindProductsUseCaseParameters(
    val query: String,
    val page: Int
)