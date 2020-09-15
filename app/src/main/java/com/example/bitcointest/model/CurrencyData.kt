package com.example.bitcointest.model

data class CurrencyData(
    val has_tag: Boolean,
    val icon: String,
    val id: Int,
    val international_price: Any,
    val name: String,
    val persian_name: String,
    val show_precision: Int,
    val symbol: String,
    val withdraw_fee: Double
)