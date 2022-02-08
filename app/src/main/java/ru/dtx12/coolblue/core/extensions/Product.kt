package ru.dtx12.coolblue.core.extensions

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
import ru.dtx12.coolblue.core.domain.models.Product

fun Product.getPriceCharSequence(): CharSequence {
    val formattedDiscountedPrice = salesPriceIncVat.formatToPrice()
    val formattedListPrice = listPriceIncVat?.formatToPrice() ?: return formattedDiscountedPrice
    val spanned = SpannableStringBuilder()
    spanned.append(formattedDiscountedPrice)
    spanned.append(" ")
    spanned.append(formattedListPrice, StrikethroughSpan(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    return spanned
}