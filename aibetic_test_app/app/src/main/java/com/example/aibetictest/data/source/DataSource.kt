package com.example.aibetictest.data.source

import com.example.aibetictest.common.annotations.DateFormat
import com.example.aibetictest.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getExchangeRates(@DateFormat date: String): Flow<ExchangeRates>
}