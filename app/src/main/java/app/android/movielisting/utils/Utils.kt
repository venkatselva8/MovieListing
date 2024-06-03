package app.android.movielisting.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import app.android.movielisting.R

object Utils {

    fun getHighlightedText(text: String, query: String, context: Context): SpannableString {
        val spannable = SpannableString(text)
        val start = text.lowercase().indexOf(query.lowercase())
        if (start >= 0) {
            val end = start + query.length
            val highlightColor = ContextCompat.getColor(context, R.color.highlight_color)
            spannable.setSpan(ForegroundColorSpan(highlightColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannable
    }

    private val posterImageMap = mapOf(
        "poster1.jpg" to R.drawable.poster1,
        "poster2.jpg" to R.drawable.poster2,
        "poster3.jpg" to R.drawable.poster3,
        "poster4.jpg" to R.drawable.poster4,
        "poster5.jpg" to R.drawable.poster5,
        "poster6.jpg" to R.drawable.poster6,
        "poster7.jpg" to R.drawable.poster7,
        "poster8.jpg" to R.drawable.poster8,
        "poster9.jpg" to R.drawable.poster9
    )

    fun getMoviePosterResourceId(posterImage: String?): Int {
        return posterImageMap[posterImage] ?: R.drawable.placeholder_for_missing_posters
    }
}
