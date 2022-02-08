package ru.dtx12.coolblue.core.data.endpoints

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dtx12.coolblue.core.data.models.ApiProductsPage

interface SearchApi {

    @GET("mobile-assignment/search")
    suspend fun searchForProducts(
        @Query("query") query: String,
        @Query("page") page: Int
    ): ApiProductsPage
}