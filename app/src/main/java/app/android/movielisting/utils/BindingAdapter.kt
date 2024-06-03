package app.android.movielisting.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.android.movielisting.R
import com.bumptech.glide.Glide

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
    val resourceId = Utils.getMoviePosterResourceId(posterImage)
    Glide.with(this.context)
        .load(resourceId)
        .placeholder(R.drawable.placeholder_for_missing_posters)
        .error(R.drawable.placeholder_for_missing_posters)
        .into(this)
}
