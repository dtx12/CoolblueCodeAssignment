package ru.dtx12.coolblue.core.extensions

import java.math.BigDecimal
import java.text.DecimalFormat

fun BigDecimal.formatToPrice(): String {
    val decimalFormat = DecimalFormat("0.00")
    return decimalFormat.format(this)
}