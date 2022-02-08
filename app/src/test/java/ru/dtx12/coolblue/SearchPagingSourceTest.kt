package ru.dtx12.coolblue

import androidx.paging.LoadType
import androidx.paging.PagingSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.core.domain.models.ProductsPage
import ru.dtx12.coolblue.core.domain.models.ReviewInformation
import ru.dtx12.coolblue.core.domain.models.ReviewSummary
import ru.dtx12.coolblue.features.search.SearchPagingSource
import java.math.BigDecimal

class SearchPagingSourceTest {

    @Test
    fun `test that paging source loads correct pages`() = runBlocking {
        val mockedPage1 = ProductsPage(
            products = listOf(
                createFakeProduct(0),
                createFakeProduct(1)
            ),
            currentPage = 1,
            pageSize = 2,
            totalResults = 4,
            pageCount = 2
        )

        val mockedPage2 = ProductsPage(
            products = listOf(
                createFakeProduct(2),
                createFakeProduct(3)
            ),
            currentPage = 2,
            pageSize = 2,
            totalResults = 4,
            pageCount = 2
        )

        val pageLoader: suspend (Int) -> ProductsPage = {
            when (it) {
                1 -> {
                    mockedPage1
                }
                2 -> {
                    mockedPage2
                }
                else -> throw IllegalStateException()
            }
        }

        val source = SearchPagingSource(pageLoader)
        val loadedFirstPage = source.load(PagingSource.LoadParams.Refresh(1, 2, false))
        val expectedFirstPage = PagingSource.LoadResult.Page(mockedPage1.products, null, 2)
        Assert.assertEquals(expectedFirstPage, loadedFirstPage)

        val loadedSecondPage = source.load(PagingSource.LoadParams.Refresh(2, 2, false))
        val expectedSecondPage = PagingSource.LoadResult.Page(mockedPage2.products, 1, null)
        Assert.assertEquals(loadedSecondPage, expectedSecondPage)
    }

    private fun createFakeProduct(id: Int): Product {
        return Product(
            productId = id,
            productName = "productName",
            reviewInformation = ReviewInformation(listOf(), ReviewSummary(
                5.0, 5
            )),
            description = listOf(),
            availabilityState = 2,
            salesPriceIncVat = BigDecimal(0L),
            productImage = "productImage",
            nextDayDelivery = true
        )
    }
}