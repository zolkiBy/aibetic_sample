package com.example.aibetictest.data.model.dto

import com.example.aibetictest.data.model.ExchangeRates
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
class GetHistoricalExchangeRatesResponse(
    @Suppress("unused") @SerialName("disclaimer") val disclaimer: String,
    @Suppress("unused") @SerialName("license") val licence: String,
    @SerialName("timestamp") val timestamp: Long,
    @Suppress("unused") @SerialName("base") val base: String,
    @SerialName("rates") val rates: Map<String, Double>,
) {
    fun toExchangeRates(): ExchangeRates {
        val instant = Instant.fromEpochSeconds(timestamp)
        val dateFormat = SimpleDateFormat("MMM" ,Locale.UK)
        val formattedDate = dateFormat.format(Date(instant.toEpochMilliseconds()))
        return ExchangeRates(timestamp, formattedDate, rates)
    }
}