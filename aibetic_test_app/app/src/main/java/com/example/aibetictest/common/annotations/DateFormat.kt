package com.example.aibetictest.common.annotations

import androidx.annotation.StringDef

const val HISTORICAL_DATE_FORMATTER = "YYYY-MM-DD"

@Retention(AnnotationRetention.SOURCE)
@StringDef(HISTORICAL_DATE_FORMATTER)
annotation class DateFormat