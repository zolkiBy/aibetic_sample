package com.example.aibetictest.data.repository

import com.example.aibetictest.common.Result
import com.example.aibetictest.common.annotations.DateFormat
import com.example.aibetictest.common.di.NAME_LOCAL_DATA_SOURCE
import com.example.aibetictest.common.di.NAME_REMOTE_DATA_SOURCE
import com.example.aibetictest.common.utils.withResult
import com.example.aibetictest.data.model.ExchangeRates
import com.example.aibetictest.data.source.DataSource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import timber.log.Timber

@Single
class ExchangeRatesRepositoryImpl(
    @Named(NAME_REMOTE_DATA_SOURCE) val remoteDataSource: DataSource,
    @Named(NAME_LOCAL_DATA_SOURCE) val localDataSource: DataSource, // can be used for store data in local storage and retrieve it
) : ExchangeRatesRepository {
    @OptIn(FlowPreview::class)
    override fun getExchangeRates(@DateFormat vararg dates: String): Flow<Result<List<ExchangeRates>>> =
        flow {
            Timber.d("Formatted dates before sending request: $dates")
            val rateItems = dates.asFlow()
                .flatMapMerge { date -> remoteDataSource.getExchangeRates(date) }
                .toList()

            emit(rateItems)
        }.map { rates ->
            withResult {
                rates
            }
        }
}
