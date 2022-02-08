package ru.dtx12.coolblue.core.data.models

data class ApiReviewInformation(
    val reviews: List<ApiReview>,
    val reviewSummary: ApiReviewSummary
)