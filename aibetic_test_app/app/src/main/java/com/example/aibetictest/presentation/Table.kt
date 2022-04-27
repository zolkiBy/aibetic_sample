package com.example.aibetictest.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> Table(
    columnCount: Int,
    rowCount: Int,
    data: List<T>,
    modifier: Modifier = Modifier,
    columnHeaders: @Composable (index: Int) -> Unit,
    rowHeaders: @Composable (index: Int) -> Unit,
    cellContent: @Composable (index: Int, item: T) -> Unit,
) {
    Surface(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.padding(16.dp)
        ) {
            items((0 until columnCount).toList()) { columnIndex ->
                Column {
                    (0 until rowCount).forEach { rowIndex ->
                        Surface(
                            border = BorderStroke(1.dp, Color.LightGray),
                            contentColor = Color.Transparent,
                            modifier = Modifier.width(150.dp)
                        ) {
                            if (columnIndex == 0) {
                                rowHeaders(rowIndex)
                            } else {
                                if (rowIndex == 0) {
                                    columnHeaders(columnIndex)
                                } else {
                                    cellContent(rowIndex, data[columnIndex - 1])
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderCell(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Black,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun ContentCell(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
