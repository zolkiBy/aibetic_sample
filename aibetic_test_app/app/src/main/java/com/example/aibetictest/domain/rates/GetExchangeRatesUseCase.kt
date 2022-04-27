package com.example.aibetictest.domain.rates

import com.example.aibetictest.common.Result
import com.example.aibetictest.common.di.NAME_DISPATCHER_IO
import com.example.aibetictest.common.exceptions.YearOutOfBoundException
import com.example.aibetictest.data.model.ExchangeRates
import com.example.aibetictest.data.repository.ExchangeRatesRepository
import com.example.aibetictest.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@Factory
class GetExchangeRatesUseCase(
    @Named(NAME_DISPATCHER_IO) coroutineDispatcher: CoroutineDispatcher,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val clock: Clock,
) :
    FlowUseCase<GetExchangeRatesUseCase.Params, List<ExchangeRates>>(coroutineDispatcher) {

    override fun execute(parameters: Params): Flow<Result<List<ExchangeRates>>> {
        val currentDate = clock.now().toLocalDateTime(TimeZone.UTC)
        val currentYear = currentDate.year
        val dates: List<String> = parameters.year?.let { yearFromParams ->
            if (yearFromParams in YEAR_LOWER_THRESHOLD..currentYear) {
                if (yearFromParams == currentYear) {
                    getFormattedDatesForCurrentYear(currentDate, currentYear)
                } else {
                    getFormattedDates(LAST_MONTH, yearFromParams)
                }
            } else {
                throw YearOutOfBoundException()
            }
        } ?: run { getFormattedDatesForCurrentYear(currentDate, currentYear) }

        return exchangeRatesRepository.getExchangeRates(*dates.toTypedArray()).conflate()
    }

    private fun getFormattedDatesForCurrentYear(currentDate: LocalDateTime, year: Int): List<String> {
        val currentMonthIndex = currentDate.month.ordinal + 1

        return getFormattedDates(currentMonthIndex, year)
    }

    private fun getFormattedDates(numberOfMonths: Int, year: Int): List<String> {
        val resultDates = mutableListOf<String>()
        for (monthIndex in 1..numberOfMonths) {
            val instant = LocalDateTime(year, monthIndex, 1, 10, 0)
                .toInstant(TimeZone.UTC)
            val dateFormat = SimpleDateFormat(DATE_FORMATTER, Locale.UK)
            val date = dateFormat.format(Date(instant.toEpochMilliseconds())).also {
                Timber.d("Formatted date for request: $it")
            }
            resultDates.add(date)
        }

        return resultDates
    }

    data class Params(val year: Int?)

    companion object {
        private const val YEAR_LOWER_THRESHOLD = 1999
        private const val LAST_MONTH = 12
        private const val DATE_FORMATTER = "yyyy-MM-dd"
    }
}
