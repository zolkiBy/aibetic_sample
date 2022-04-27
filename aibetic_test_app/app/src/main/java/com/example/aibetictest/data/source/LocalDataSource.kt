package com.example.aibetictest.data.source

import com.example.aibetictest.common.di.NAME_LOCAL_DATA_SOURCE
import com.example.aibetictest.data.model.ExchangeRates
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
@Named(NAME_LOCAL_DATA_SOURCE)
internal class LocalDataSource : DataSource {
    override suspend fun getExchangeRates(date: String): Flow<ExchangeRates> {
        TODO("Not yet implemented")
    }
}