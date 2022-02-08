package ru.dtx12.coolblue.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.databinding.ItemProductBinding

class SearchAdapter constructor(
    private val viewLifecycleOwner: LifecycleOwner
) : PagingDataAdapter<Product, ProductViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) ?: throw IllegalStateException() // no placeholders allowed
        holder.bind(product, viewLifecycleOwner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}