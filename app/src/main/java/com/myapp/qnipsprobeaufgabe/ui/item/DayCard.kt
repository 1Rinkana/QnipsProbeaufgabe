package com.myapp.qnipsprobeaufgabe.ui.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.myapp.qnipsprobeaufgabe.data.JsonData

@Composable
fun DayCard(menu: JsonData, rowIndex: Int) {
    menu.rows[rowIndex].days.forEach { day ->
        var showProducts by remember { mutableStateOf(false) }
        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .clickable { showProducts = !showProducts }
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WeekDay(dayIndex = day.weekday)

                if (day.productIds.isEmpty()) {
                    AnimatedVisibility(visible = showProducts) {
                        Text(
                            text = "An diesem Tag gibt es kein ${menu.rows[rowIndex].name}.",
                            modifier = Modifier,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                } else {
                    day.productIds.forEach { productId ->
                        menu.products.values
                            .filter { it.productId == productId.id }
                            .forEach { product ->
                                var showAllergens by remember { mutableStateOf(false) }

                                AnimatedVisibility(visible = showProducts) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(16.dp))
                                            .background(MaterialTheme.colorScheme.surface)
                                            .clickable {
                                                showAllergens = !showAllergens
                                            }
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = product.name,
                                            modifier = Modifier,
                                            fontWeight = FontWeight.SemiBold,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.titleMedium,
                                        )

                                        val hintText = if (showAllergens) "Tap to hide" else "Tap to show"

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Allergens: ",
                                                modifier = Modifier,
                                                fontWeight = FontWeight.SemiBold,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.bodyLarge,
                                            )

                                            Spacer(modifier = Modifier.padding(2.dp))

                                            Surface(
                                                modifier = Modifier,
                                                color = if (showAllergens)
                                                    MaterialTheme.colorScheme.errorContainer else
                                                    MaterialTheme.colorScheme.secondaryContainer,
                                                shape = RoundedCornerShape(8)
                                            ) {
                                                Text(
                                                    text = hintText,
                                                    modifier = Modifier.padding(4.dp),
                                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                                    fontWeight = FontWeight.Bold,
                                                    overflow = TextOverflow.Ellipsis,
                                                    style = MaterialTheme.typography.titleMedium,
                                                )
                                            }
                                        }

                                        val allergens = menu.allergens.values
                                            .filter { it.id in product.allergenIds }
                                            .joinToString(", ") { it.label }

                                        AnimatedVisibility(visible = showAllergens) {
                                            Text(
                                                text = allergens,
                                                modifier = Modifier,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Normal,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
                                        }

                                        Spacer(modifier = Modifier.padding(4.dp))

                                        val prise = if (product.price.betrag != 0.0)
                                            "$${product.price.betrag}$" else "Kostenlos"

                                        Text(
                                            text = prise,
                                            modifier = Modifier,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyLarge,
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}
