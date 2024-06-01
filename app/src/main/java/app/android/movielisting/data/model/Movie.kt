package app.android.movielisting.data.model

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "name") val name: String,
    @Json(name = "poster-image") val posterImage: String
)
