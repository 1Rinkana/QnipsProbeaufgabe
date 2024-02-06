@file:OptIn(ExperimentalFoundationApi::class)

package com.myapp.qnipsprobeaufgabe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.qnipsprobeaufgabe.dto.ClientAPI
import com.myapp.qnipsprobeaufgabe.dto.repository.MenuRepositoryImpl
import com.myapp.qnipsprobeaufgabe.ui.item.WeekDay
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MenuViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MenuViewModel(MenuRepositoryImpl(ClientAPI.createApi())) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen(viewModel)
        }
        viewModel.loadData()
    }
}

@Composable
fun AppScreen(viewModel: MenuViewModel) {
    val state by viewModel.state.collectAsState()

    Surface {
        when(state) {
            is MenuState.LoadingMenuState -> LoadingScreen()
            is MenuState.ReadyMenuState -> SuccessScreen(viewModel)
            is MenuState.ErrorMenuState -> ErrorScreen(viewModel)
        }
    }
}

@Composable
fun LoadingScreen() {

}

@Composable
fun SuccessScreen(viewModel: MenuViewModel) {
    val state by viewModel.state.collectAsState()
    val readyMenuState = state as MenuState.ReadyMenuState
    val menu = readyMenuState.menu

    val titles = menu.rows.map { it.name }
    var tabRowIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { titles.size }
    
    LaunchedEffect(tabRowIndex) {
        pagerState.animateScrollToPage(tabRowIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            tabRowIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = tabRowIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = tabRowIndex == index,
                    onClick = { tabRowIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
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
                            shape = RoundedCornerShape(4.dp),
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
                                            )

                                            Text(
                                                text = "$${product.price.betrag}",
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
}

@Composable
fun ErrorScreen(viewModel: MenuViewModel) {

}
