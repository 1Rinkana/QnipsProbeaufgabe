package com.myapp.qnipsprobeaufgabe.ui.item

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun WeekDay(dayIndex: Int) {
    val day = when(dayIndex) {
        0 -> "Montag"
        1 -> "Dienstag"
        2 -> "Mittwoch"
        3 -> "Donnerstag"
        4 -> "Freitag"
        5 -> "Samstag"
        6 -> "Sonntag"
        else -> ""
    }
    Text(
        text = day,
        modifier = Modifier,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge,
    )
}