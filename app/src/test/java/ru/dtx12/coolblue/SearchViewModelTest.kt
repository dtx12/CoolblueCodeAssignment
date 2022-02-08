package ru.dtx12.coolblue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import ru.dtx12.coolblue.core.domain.usecases.FindProductsUseCase
import ru.dtx12.coolblue.core.exceptions.NoInternetException
import ru.dtx12.coolblue.features.search.SearchViewModel

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel

    @MockK private lateinit var findProductsUseCase: FindProductsUseCase

    private val mainThreadSurrogate = newSingleThreadContext("main")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        searchViewModel = SearchViewModel(findProductsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `exception during loading data should be handled`() = runBlocking {
        coEvery { findProductsUseCase.execute(any()) } throws NoInternetException()
        searchViewModel.searchForProducts("apple")
        searchViewModel.handleAdapterState(
            CombinedLoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
                source = LoadStates(
                    LoadState.Error(NoInternetException()),
                    LoadState.Error(NoInternetException()),
                    LoadState.Error(NoInternetException()),
                )
            )
        )
        searchViewModel.errorEvent.observeForever {
            Assert.assertTrue(it.value is NoInternetException)
        }
    }
}