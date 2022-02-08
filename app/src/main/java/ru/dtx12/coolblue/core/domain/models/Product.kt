package ru.dtx12.coolblue.core.domain.models

import java.math.BigDecimal

data class Product(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformation,
    val description: List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: BigDecimal,
    val productImage: String,
    val nextDayDelivery: Boolean,
    val promoIcon: PromoIcon? = null,
    val coolbluesChoiceInformationTitle: String? = null,
    val listPriceIncVat: BigDecimal? = null,
    val listPriceExVat: BigDecimal? = null
)