package com.example.aibetictest.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.aibetictest.common.net.ExchangeRatesApi
import com.example.aibetictest.common.utils.onError
import com.example.aibetictest.common.utils.onSuccess
import com.example.aibetictest.data.model.ExchangeRates
import com.example.aibetictest.domain.rates.GetSpecificNumberOfExchangeRatesUseCase
import com.example.aibetictest.presentation.base.BaseReducer
import com.example.aibetictest.presentation.base.BaseViewModel
import com.example.aibetictest.presentation.base.Event
import com.example.aibetictest.presentation.base.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class MainViewModel(private val getSpecificNumberOfExchangeRatesUseCase: GetSpecificNumberOfExchangeRatesUseCase, private val api: ExchangeRatesApi) :
    BaseViewModel<MainScreenState, MainScreenEvent, MainViewModel.MainScreenReducer>() {

    override val reducer: MainScreenReducer = MainScreenReducer(MainScreenState.initial())
    override val state: Flow<MainScreenState>
        get() = reducer.state

    init {
        loadExchangeRates()
    }

    private fun loadExchangeRates() {
        viewModelScope.launch {
            while (true) {
                sendEvent(MainScreenEvent.Loading)
                delay(DELAY_FOR_SHOW_LOADING)
                getSpecificNumberOfExchangeRatesUseCase(
                    GetSpecificNumberOfExchangeRatesUseCase.Params(
                        2022, // change year here, unfortunately don't have enough time to create UI for changing year
                        MANDATORY_CURRENCY,
                        NUMBER_OF_CURRENCIES,
                    )
                )
                    .onSuccess { rates ->
                        rates.forEach { exchangeRates ->
                            Timber.d("Loaded rates for month ${exchangeRates.month}, filtered rates - ${exchangeRates.filteredRates}")
                        }
                        sendEvent(MainScreenEvent.DataLoaded(rates))
                    }
                    .onError { throwable ->
                        Timber.e(throwable)
                        sendEvent(MainScreenEvent.ErrorLoadingData(throwable))
                    }
                    .collect()
                delay(REPEAT_PERIOD)
            }
        }
    }

    // also can combine flows to load data
    @Suppress("unused")
    private fun loadExchangeRatesInReactively() {
        var yearForDataLoading = 2022
        viewModelScope.launch {
            timerFlow(REPEAT_PERIOD).combine(
                getSpecificNumberOfExchangeRatesUseCase(
                    GetSpecificNumberOfExchangeRatesUseCase.Params(
                        yearForDataLoading,
                        MANDATORY_CURRENCY,
                        NUMBER_OF_CURRENCIES,
                    )
                )
            ) { counter, rates ->
                yearForDataLoading = if (counter % 2 == 0L) 2021 else 2022
                Timber.d("Transform when combining two flows, counter - $counter")
                rates
            }
                .onEach {
                    Timber.d("Send loading on each emission")
                    sendEvent(MainScreenEvent.Loading)
                }
                .onSuccess { rates ->
                    rates.forEach { exchangeRates ->
                        Timber.d("Loaded rates for month ${exchangeRates.month}, filtered rates - ${exchangeRates.filteredRates}")
                    }
                    sendEvent(MainScreenEvent.DataLoaded(rates))
                }
                .onError { throwable ->
                    Timber.e(throwable)
                    sendEvent(MainScreenEvent.ErrorLoadingData(throwable))
                }
                .collect()
        }
    }

    @Suppress("SameParameterValue")
    private fun timerFlow(repeatPeriod: Duration): Flow<Long> {
        return flow {
            var counter = 0L
            while (true) {
                emit(counter)
                counter++
                delay(repeatPeriod)
            }
        }
    }

    companion object {
        private const val MANDATORY_CURRENCY = "EUR"
        private const val NUMBER_OF_CURRENCIES = 10
        private val REPEAT_PERIOD = 20.seconds
        private val DELAY_FOR_SHOW_LOADING = 1.seconds
    }

    class MainScreenReducer(initial: MainScreenState) : BaseReducer<MainScreenState, MainScreenEvent>(initial) {
        override fun reduce(oldState: MainScreenState, event: MainScreenEvent) {
            Timber.d("Call reduce function with event: $event and state: $state")
            when (event) {
                is MainScreenEvent.Loading -> setState(oldState.copy(isLoading = true, rates = emptyList(), showError = false))
                is MainScreenEvent.DataLoaded -> setState(oldState.copy(isLoading = false, rates = event.rates, showError = false))
                is MainScreenEvent.ErrorLoadingData -> setState(oldState.copy(isLoading = false, rates = emptyList(), showError = true))
            }
        }
    }
}

@Immutable
data class MainScreenState(val isLoading: Boolean, val rates: List<ExchangeRates>, val showError: Boolean) : UiState {

    companion object {
        fun initial() = MainScreenState(
            isLoading = true,
            rates = emptyList(),
            showError = false,
        )
    }
}

@Immutable
sealed class MainScreenEvent : Event {
    data class DataLoaded(val rates: List<ExchangeRates>) : MainScreenEvent()
    data class ErrorLoadingData(val exception: Throwable) : MainScreenEvent()
    object Loading : MainScreenEvent()
}