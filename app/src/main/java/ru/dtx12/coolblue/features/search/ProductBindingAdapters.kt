package ru.dtx12.coolblue.features.search

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ru.dtx12.coolblue.R
import ru.dtx12.coolblue.core.domain.models.Product
import ru.dtx12.coolblue.core.domain.models.PromoIcon
import ru.dtx12.coolblue.core.domain.models.ReviewSummary
import ru.dtx12.coolblue.core.extensions.getPriceCharSequence
import ru.dtx12.coolblue.core.extensions.visible

object ProductBindingAdapters {

    @JvmStatic
    @BindingAdapter("loadImageByUrl")
    fun bindImageByUrl(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view).load(url).into(view)
        } else {
            view.setImageBitmap(null)
        }
    }

    @JvmStatic
    @BindingAdapter("visibleIfNotNull")
    fun bindVisibleIfNotNull(view: View, any: Any?) {
        view.visible(any != null)
    }

    @JvmStatic
    @BindingAdapter("visibleIfTrue")
    fun bindVisibleIfTrue(view: View, visible: Boolean?) {
        view.visible(visible == true)
    }

    @JvmStatic
    @BindingAdapter("reviewsRating")
    fun bindReviewsRating(ratingView: RatingBar, reviewSummary: ReviewSummary?) {
        if (reviewSummary == null) {
            ratingView.rating = 0.0F
        } else {
            ratingView.rating = reviewSummary.reviewAverage.toFloat() / 2
        }
    }

    @JvmStatic
    @BindingAdapter("reviewsCount")
    fun bindReviewsCount(view: TextView, reviewSummary: ReviewSummary?) {
        if (reviewSummary == null) {
            view.text = null
        } else {
            view.text = view.resources.getQuantityString(
                R.plurals.reviews_count,
                reviewSummary.reviewCount,
                reviewSummary.reviewCount
            )
        }
    }

    @JvmStatic
    @BindingAdapter("productDescription")
    fun bindProductDescription(view: TextView, lines: List<String>?) {
        if (lines == null) {
            view.text = null
        } else {
            view.text = lines.joinToString(separator = "\n") { "• $it" }
        }
    }

    @JvmStatic
    @BindingAdapter("productPrice")
    fun bindProductPrice(view: TextView, product: Product?) {
        if (product == null) {
            view.text = null
        } else {
            view.text = product.getPriceCharSequence()
        }
    }

    @JvmStatic
    @BindingAdapter("productPromo")
    fun bindProductPromo(view: TextView, promoIcon: PromoIcon?) {
        if (promoIcon == null) {
            view.visible(false)
        } else {
            view.visible(true)
            when (promoIcon.type) {
                "coolblues-choice" -> {
                    view.setText(R.string.search_cb_choice)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.purple_700))
                }
                else -> {
                    view.setText(R.string.search_promotion)
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.orange))
                }
            }
        }
    }
}