package com.myapp.qnipsprobeaufgabe.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.myapp.qnipsprobeaufgabe.ui.item.WeekDay
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(viewModel: MenuViewModel) {
    val state by viewModel.state.collectAsState()
    val readyMenuState = state as MenuState.ReadyMenuState
    val menu = readyMenuState.menu

    val titles = menu.rows.map { it.name }
    var tabRowIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { titles.size }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(tabRowIndex) {
        pagerState.animateScrollToPage(tabRowIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            tabRowIndex = pagerState.currentPage
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Speiseplan",
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            PrimaryTabRow(selectedTabIndex = tabRowIndex) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabRowIndex == index,
                        onClick = { tabRowIndex = index },
                        modifier = Modifier
                            .clip(CircleShape),
                        text = {
                            Text(
                                text = title,
                                modifier = Modifier,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleMedium,
                                onTextLayout = {},
                            )
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { rowIndex ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    item {
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
                }
            }
        }
    }
}
