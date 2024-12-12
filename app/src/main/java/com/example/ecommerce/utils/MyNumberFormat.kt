package com.example.ecommerce.utils

import java.text.NumberFormat
import java.util.Locale

object MyNumberFormat {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY)

    fun doubleToStringEuros(value: Double): String {
        return numberFormat.format(value)
    }


}