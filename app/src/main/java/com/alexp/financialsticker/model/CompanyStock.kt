package com.alexp.financialsticker.model

data class CompanyStock(
    val name: String,
    val companyName: String,
    var price: Double,
    var change: Double,
    var changePercent: Double,
    val marketCap: String
)