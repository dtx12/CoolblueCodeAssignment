package ru.dtx12.coolblue.core.data

import ru.dtx12.coolblue.core.data.models.*
import ru.dtx12.coolblue.core.domain.models.*
import java.math.BigDecimal
import java.math.RoundingMode


fun ApiProductsPage.toProductPage(): ProductsPage {
    return ProductsPage(
        products = products.map { it.toProduct() },
        currentPage = currentPage,
        pageSize = pageSize,
        totalResults = totalResults,
        pageCount = pageCount
    )
}

private fun ApiProduct.toProduct(): Product {
    return Product(
        productId = productId,
        productName = productName,
        reviewInformation = reviewInformation.toReviewInformation(),
        description = description,
        availabilityState = availabilityState,
        salesPriceIncVat = salesPriceIncVat.toBigDecimalWithScale(),
        productImage = productImage,
        nextDayDelivery = nextDayDelivery,
        promoIcon = promoIcon?.toPromoIcon(),
        coolbluesChoiceInformationTitle = coolbluesChoiceInformationTitle,
        listPriceIncVat = listPriceIncVat?.toBigDecimalWithScale(),
        listPriceExVat = listPriceExVat?.toBigDecimalWithScale()
    )
}

private fun Double.toBigDecimalWithScale(): BigDecimal {
    return BigDecimal.valueOf(this).setScale(2, RoundingMode.HALF_UP)
}

private fun ApiPromoIcon.toPromoIcon(): PromoIcon {
    return PromoIcon(
        text = text,
        type = type
    )
}

private fun ApiReview.toReview(): Review {
    return Review()
}

private fun ApiReviewInformation.toReviewInformation(): ReviewInformation {
    return ReviewInformation(
        reviews = reviews.map { it.toReview() },
        reviewSummary = reviewSummary.toReviewSummary()
    )
}

private fun ApiReviewSummary.toReviewSummary(): ReviewSummary {
    return ReviewSummary(
        reviewAverage = reviewAverage,
        reviewCount = reviewCount
    )
}