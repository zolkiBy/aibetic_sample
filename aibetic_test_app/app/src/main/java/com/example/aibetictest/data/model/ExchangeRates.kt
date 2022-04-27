package com.example.aibetictest.data.model

data class ExchangeRates(val timestamp: Long, val month: String, val rates: Map<String, Double>) {
    var filteredRates: MutableList<Pair<String, Double>> = mutableListOf()
}