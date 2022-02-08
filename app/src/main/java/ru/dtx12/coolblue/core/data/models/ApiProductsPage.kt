package ru.dtx12.coolblue.core.data.models

data class ApiProductsPage(
    val products: List<ApiProduct>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)