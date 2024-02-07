package com.myapp.qnipsprobeaufgabe.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.myapp.qnipsprobeaufgabe.dto.JsonData

@Composable
fun DayCard(menu: JsonData, rowIndex: Int) {
    menu.rows[rowIndex].days.forEach { day ->
        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            day.productIds.forEach { productId ->
                menu.products.values
                    .filter { it.productId == productId.id }
                    .forEach { product ->
                        Column(modifier = Modifier.padding(8.dp)) {
                            WeekDay(dayIndex = day.weekday)

                            Text(
                                text = product.name,
                                modifier = Modifier,
                                fontWeight = FontWeight.Normal,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium,
                                onTextLayout = {},
                            )

                            val allergens = menu.allergens.values
                                .filter { it.id in product.allergenIds }
                                .joinToString(", ") { it.label }

                            Text(
                                text = "Allergens: $allergens",
                                modifier = Modifier,
                                fontWeight = FontWeight.Normal,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyLarge,
                                onTextLayout = {},
                            )

                            Text(
                                text = "$${product.price.betrag}",
                                modifier = Modifier,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyLarge,
                                onTextLayout = {},
                            )
                        }
                    }
            }
        }
    }
}