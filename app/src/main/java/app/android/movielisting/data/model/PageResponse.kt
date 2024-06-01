package app.android.movielisting.data.model

import com.squareup.moshi.Json

data class PageResponse(
    @Json(name = "page") val page: Page
)

data class Page(
    @Json(name = "title") val title: String,
    @Json(name = "total-content-items") val totalContentItems: String,
    @Json(name = "page-num") val pageNum: String,
    @Json(name = "page-size") val pageSize: String,
    @Json(name = "content-items") val contentItems: ContentItems
)

data class ContentItems(
    @Json(name = "content") val content: List<Movie>
)