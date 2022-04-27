package com.example.aibetictest.domain.rates

import com.example.aibetictest.common.Result
import com.example.aibetictest.common.data
import com.example.aibetictest.common.di.NAME_DISPATCHER_IO
import com.example.aibetictest.common.exceptions.EmptyDataException
import com.example.aibetictest.common.succeeded
import com.example.aibetictest.common.utils.withResult
import com.example.aibetictest.data.model.ExchangeRates
import com.example.aibetictest.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import java.io.IOException

@Factory
class GetSpecificNumberOfExchangeRatesUseCase(
    @Named(NAME_DISPATCHER_IO) coroutineDispatcher: CoroutineDispatcher,
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase,
) : FlowUseCase<GetSpecificNumberOfExchangeRatesUseCase.Params, List<ExchangeRates>>(coroutineDispatcher) {
    override fun execute(parameters: Params): Flow<Result<List<ExchangeRates>>> {
        return getExchangeRatesUseCase.invoke(GetExchangeRatesUseCase.Params(parameters.year))
            .map { result ->
                if (result.succeeded) {
                    result.data?.let { exchangeRatesItems ->
                        if (exchangeRatesItems.isNotEmpty()) {
                            val currencyCodes = mutableListOf<String>()
                            val allCurrencies = exchangeRatesItems[0].rates.keys.sorted()
                            var numberOfCurrencies = parameters.numberOfCurrencies

                            allCurrencies.find { item -> item == parameters.mandatoryCurrency }?.let {
                                currencyCodes.add(it)
                                numberOfCurrencies--
                            }

                            allCurrencies.filterIndexedTo(currencyCodes) { index, _ ->
                                index < numberOfCurrencies
                            }
                                .sorted()

                            exchangeRatesItems.forEach { exchangeRatesItem ->
                                currencyCodes.forEach { key ->
                                    val currencyRate = exchangeRatesItem.rates[key]
                                    if (currencyRate != null) {
                                        exchangeRatesItem.filteredRates.add(Pair(key, currencyRate))
                                    } else {
                                        exchangeRatesItem.filteredRates.add(Pair(key, 0.0))
                                    }
                                }
                            }

                            withResult {
                                exchangeRatesItems.sortedBy { rates -> rates.timestamp }
                            }
                        } else {
                            throw EmptyDataException()
                        }
                    } ?: throw EmptyDataException()
                } else {
                    throw EmptyDataException()
                }
            }
    }

    data class Params(val year: Int?, val mandatoryCurrency: String, val numberOfCurrencies: Int)
}