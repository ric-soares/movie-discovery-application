package com.example.moviediscoveryapplication.common.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.formatReleaseDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.US)

    val date: Date = inputFormat.parse(this) as Date
    val formattedDate: String = outputFormat.format(date)

    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val daySuffix = getDayOfMonthSuffix(dayOfMonth)

    return formattedDate.replaceFirst(dayOfMonth.toString(), "$dayOfMonth$daySuffix")
}

fun getDayOfMonthSuffix(n: Int): String {
    return when {
        n in 11..13 -> "th"
        n % 10 == 1 -> "st"
        n % 10 == 2 -> "nd"
        n % 10 == 3 -> "rd"
        else -> "th"
    }
}