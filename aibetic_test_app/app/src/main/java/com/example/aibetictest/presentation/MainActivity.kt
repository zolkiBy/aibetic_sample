package com.example.aibetictest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aibetictest.data.model.ExchangeRates
import com.example.aibetictest.presentation.theme.AIBeticTestTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIBeticTestTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState(MainScreenState.initial())

    Column {
        when {
            state.isLoading -> ContentWithProgress()
            state.rates.isNotEmpty() -> CurrencyContent(rates = state.rates)
            state.rates.isEmpty() -> EmptyCurrencyContent()
            state.showError -> ErrorContent()
        }
    }
}

@Composable
private fun ContentWithProgress() {
    Surface(color = Color.LightGray) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun CurrencyContent(rates: List<ExchangeRates>) {

    val currencyCodes = rates[0].filteredRates
        .map { (currencyCode, _) -> currencyCode }

    val columnHeaders: @Composable (Int) -> Unit = { index ->
        val month = when (index) {
            0 -> "Currency"
            else -> rates[index - 1].month
        }

        HeaderCell(text = month)
    }

    val rowHeaders: @Composable (Int) -> Unit = { index ->
        val header = when (index) {
            0 -> "Currency"
            else -> currencyCodes[index - 1]
        }

        HeaderCell(text = header)
    }

    val cellContent: @Composable (Int, ExchangeRates) -> Unit = { index, exchangeRates ->
        val currencyRate = if (exchangeRates.filteredRates[index - 1].second == 0.0) {
            "-"
        } else {
            exchangeRates.filteredRates[index - 1].second.toString()
        }

        ContentCell(text = currencyRate)
    }

    Table(
        columnCount = rates.size + 1,
        rowCount = currencyCodes.size + 1,
        data = rates,
        modifier = Modifier.verticalScroll(rememberScrollState()),
        columnHeaders = columnHeaders,
        rowHeaders = rowHeaders,
        cellContent = cellContent,
    )
}

@Composable
private fun EmptyCurrencyContent() {
    // TODO: create empty content composable
}

@Composable
private fun ErrorContent() {
    // TODO: create error content composable
}

@Preview
@Composable
fun ProgressPreview() {
    AIBeticTestTheme {
        ContentWithProgress()
    }
}
