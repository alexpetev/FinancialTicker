package com.alexp.financialsticker.model

data class DeltasStock(
    val sleepTime: Long,
    val blank: String,
    val price: Double?,
    val change: Double?,
    val changePercent: Double?
)