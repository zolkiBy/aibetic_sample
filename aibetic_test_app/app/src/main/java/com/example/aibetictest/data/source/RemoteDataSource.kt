package com.example.aibetictest.data.source

import com.example.aibetictest.common.annotations.DateFormat
import com.example.aibetictest.common.di.NAME_REMOTE_DATA_SOURCE
import com.example.aibetictest.common.net.ExchangeRatesApi
import com.example.aibetictest.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import timber.log.Timber

@Single
@Named(NAME_REMOTE_DATA_SOURCE)
internal class RemoteDataSource(private val exchangeRatesApi: ExchangeRatesApi) : DataSource {
    override suspend fun getExchangeRates(@DateFormat date: String): Flow<ExchangeRates> {
        return flowOf(exchangeRatesApi.getHistoricalExchangeRate(date))
            .map { response -> response.toExchangeRates() }
            .also { Timber.d("Formatted date for individual request: $date") }
    }
}