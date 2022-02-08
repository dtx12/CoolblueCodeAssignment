package ru.dtx12.coolblue.core.domain.models

data class ReviewInformation(
    val reviews: List<Review>,
    val reviewSummary: ReviewSummary
)