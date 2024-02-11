package com.myapp.qnipsprobeaufgabe

import com.myapp.qnipsprobeaufgabe.data.Allergen
import com.myapp.qnipsprobeaufgabe.data.Day
import com.myapp.qnipsprobeaufgabe.data.JsonData
import com.myapp.qnipsprobeaufgabe.data.Price
import com.myapp.qnipsprobeaufgabe.data.Product
import com.myapp.qnipsprobeaufgabe.data.ProductId
import com.myapp.qnipsprobeaufgabe.data.Row
import com.myapp.qnipsprobeaufgabe.repository.MenuRepository
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuState
import com.myapp.qnipsprobeaufgabe.ui.menu.MenuViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class MenuViewModelTest {

    private val repo: MenuRepository = mockk(relaxed = true)
    private  var viewModel: MenuViewModel = MenuViewModel(repo)


    private val successJsonData = JsonData(
        allergens = mapOf(
            "allergen1" to Allergen(id = "allergen1", label = "Gluten"),
            "allergen2" to Allergen(id = "allergen2", label = "Dairy"),
        ),
        products = mapOf(
            "product1" to Product(
                allergenIds = listOf("allergen1"),
                productId = 1,
                name = "Bread",
                price = Price(betrag = 2.5),
            ),
            "product2" to Product(
                allergenIds = listOf("allergen2"),
                productId = 2,
                name = "Milk",
                price = Price(betrag = 1.5),
            ),
        ),
        rows = listOf(
            Row(
                name = "Row1",
                days = listOf(
                    Day(weekday = 1, productIds = listOf(ProductId(id = 1), ProductId(id = 2))),
                    Day(weekday = 2, productIds = listOf(ProductId(id = 2))),
                )
            ),
            Row(
                name = "Row2",
                days = listOf(
                    Day(
                        weekday = 3,
                        productIds = listOf(ProductId(id = 1)),
                    )
                )
            ),
        )
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `returning a successful result changes the state to successful`() = runTest {
        //WHEN
        coEvery { repo.getMenu() } returns Result.success<JsonData>(successJsonData)

        viewModel.loadData().join()
        coVerify { repo.getMenu() }

        //THEN
        println("Current state: ${viewModel.state.value}")
        assertIs<MenuState.ReadyMenuState>(viewModel.state.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `returning a failure result changes the state to error`() = runTest {
        //WHEN
        coEvery { repo.getMenu() } returns Result.failure(Exception("Error"))

        viewModel.loadData().join()
        coVerify { repo.getMenu() }

        //THEN
        println("Current state: ${viewModel.state.value}")
        assertIs<MenuState.ErrorMenuState>(viewModel.state.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `the start state is loading`() = runTest {
        //THEN
        assertIs<MenuState.LoadingMenuState>(viewModel.state.value)
    }
}
