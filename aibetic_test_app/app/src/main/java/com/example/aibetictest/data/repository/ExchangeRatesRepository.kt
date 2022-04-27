package com.example.aibetictest.data.repository

import com.example.aibetictest.common.Result
import com.example.aibetictest.common.annotations.DateFormat
import com.example.aibetictest.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    fun getExchangeRates(@DateFormat vararg dates: String): Flow<Result<List<ExchangeRates>>>
}