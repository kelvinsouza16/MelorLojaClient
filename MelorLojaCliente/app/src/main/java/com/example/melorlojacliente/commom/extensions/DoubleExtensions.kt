package com.example.melorlojacliente.commom.extensions

import java.text.DecimalFormat
import java.util.Locale

fun Double.getFinanceType(): String {
    val value = this
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat.format(value)
}
