package br.com.raynerweb.portugal.financas.ext

import java.text.NumberFormat
import java.util.*

private const val EUR = "EUR"
internal fun Double.toCurrency(): String {
    val currencyFormatter = NumberFormat.getInstance()
    currencyFormatter.minimumFractionDigits = 2
    currencyFormatter.maximumFractionDigits = 2
    currencyFormatter.currency = Currency.getInstance(EUR)
    return currencyFormatter.format(this)
}
