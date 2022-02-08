package ru.dtx12.coolblue.features.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.core.domain.models.ProductsPage

class SearchPagingSource(
    private val pageLoader: suspend (Int) -> ProductsPage
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val position = params.key ?: 1
            val page = pageLoader.invoke(position)
            val prevPosition = if (position == 1) null else position - 1
            val nextPosition = if (page.currentPage == page.pageCount) null else position + 1
            return LoadResult.Page(
                page.products,
                prevPosition,
                nextPosition
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}