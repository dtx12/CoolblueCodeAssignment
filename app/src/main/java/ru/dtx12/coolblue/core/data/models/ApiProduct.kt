package ru.dtx12.coolblue.core.data.models

import com.google.gson.annotations.SerializedName

data class ApiProduct(
    val productId: Int,
    val productName: String,
    val reviewInformation: ApiReviewInformation,
    @SerializedName("USPs")
    val description: List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: Double,
    val productImage: String,
    val nextDayDelivery: Boolean,
    val promoIcon: ApiPromoIcon? = null,
    val coolbluesChoiceInformationTitle: String? = null,
    val listPriceIncVat: Double? = null,
    val listPriceExVat: Double? = null
)