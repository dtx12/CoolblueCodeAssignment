package ru.dtx12.coolblue.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.dtx12.coolblue.core.OneTimeEvent
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.core.domain.usecases.FindProductsUseCase
import ru.dtx12.coolblue.core.domain.usecases.FindProductsUseCaseParameters
import ru.dtx12.coolblue.core.extensions.getExceptionFromSource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val findProductsUseCase: FindProductsUseCase
) : ViewModel() {

    private companion object {
        const val PAGE_SIZE = 24
    }

    val products: Flow<PagingData<Product>>

    private val _errorEvent = MutableLiveData<OneTimeEvent<Throwable>>()
    val errorEvent: LiveData<OneTimeEvent<Throwable>> = _errorEvent

    private var productsDatasource: SearchPagingSource? = null

    private var textToSearch = ""

    init {
        val pagerConfig = PagingConfig(PAGE_SIZE, PAGE_SIZE, false, PAGE_SIZE)
        val pager = Pager(pagerConfig, 1) {
            val dataSource = SearchPagingSource {
                findProductsUseCase.execute(
                    FindProductsUseCaseParameters(
                        query = textToSearch,
                        page = it
                    )
                )
            }
            productsDatasource = dataSource
            dataSource
        }
        products = pager.flow.cachedIn(viewModelScope)
    }

    fun searchForProducts(text: String) {
        textToSearch = text.trim()
        productsDatasource?.invalidate()
    }

    fun handleAdapterState(state: CombinedLoadStates) {
        val error = state.getExceptionFromSource()
        if (error != null) {
            _errorEvent.postValue(OneTimeEvent(error))
        }
    }
}