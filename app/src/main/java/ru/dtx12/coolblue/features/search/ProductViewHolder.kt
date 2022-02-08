package ru.dtx12.coolblue.features.search

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.core.extensions.executeAfter
import ru.dtx12.coolblue.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, lifecycleOwner: LifecycleOwner) {
        binding.executeAfter {
            this.product = product
            this.lifecycleOwner = lifecycleOwner
        }
    }
}