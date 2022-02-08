package ru.dtx12.coolblue.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.dtx12.coolblue.R
import ru.dtx12.coolblue.core.OneTimeEvent
import ru.dtx12.coolblue.core.exceptions.NoInternetException
import ru.dtx12.coolblue.core.extensions.executeAfter
import ru.dtx12.coolblue.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    private val adapter by lazy {
        SearchAdapter(viewLifecycleOwner)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        val isTablet = resources.getBoolean(R.bool.is_tablet)
        binding.executeAfter {
            this.productsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchForProducts(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.searchForProducts(newText)
                    return true
                }

            })
            this.productsRecyclerView.layoutManager = if (isTablet) {
                GridLayoutManager(requireActivity(), 2)
            } else {
                LinearLayoutManager(requireActivity())
            }
            this.productsRecyclerView.adapter = adapter
            this.lifecycleOwner = viewLifecycleOwner
        }
        lifecycleScope.launch {
            viewModel.products.collect {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                viewModel.handleAdapterState(it)
            }
        }
        viewModel.errorEvent.observe(viewLifecycleOwner, ::handleErrorEvent)
        return binding.root
    }

    private fun handleErrorEvent(event: OneTimeEvent<Throwable>?) {
        when (event?.value) {
            is NoInternetException -> {
                showError(R.string.search_no_internet_connection_error)
            }
            null -> {

            }
            else -> {
                showError(R.string.search_loading_error)
            }
        }
    }

    private fun showError(errResId: Int) {
        Toast.makeText(
            requireActivity(),
            errResId,
            Toast.LENGTH_LONG
        ).show()
    }
}