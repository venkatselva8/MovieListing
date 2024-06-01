package app.android.movielisting.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.android.movielisting.R
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "placeholderImage")
fun ImageView.loadImage(imageUrl: Int, placeholderImage: Drawable) {
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .into(this)
}

@BindingAdapter("highlightedText", "searchQuery")
fun TextView.setHighlightedText(text: String, query: String) {
    this.text = if (query.isEmpty()) {
        text
    } else {
        Utils.getHighlightedText(text, query, this.context)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(posterImage: String?) {
    if (posterImage != null) {
        val resourceId = Utils.getMoviePosterResourceId(posterImage)
        this.setImageResource(resourceId)
    } else {
        this.setImageResource(R.drawable.placeholder_for_missing_posters)
    }
}