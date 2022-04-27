package com.example.aibetictest.common.net

import com.example.aibetictest.common.annotations.DateFormat
import com.example.aibetictest.data.model.dto.GetHistoricalExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path

private const val PATH_DATE = "date"

interface ExchangeRatesApi {

    @GET("historical/{${PATH_DATE}}.json")
    suspend fun getHistoricalExchangeRate(@Path(PATH_DATE) @DateFormat date: String): GetHistoricalExchangeRatesResponse
}