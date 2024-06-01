package app.android.movielisting.data.repository

import android.content.Context
import app.android.movielisting.data.model.Movie
import app.android.movielisting.data.model.PageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

/**
 * Repository class to handle data operations related to movies.
 */
class MovieRepository {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(PageResponse::class.java)

    /**
     * Fetches movies from the assets folder.
     *
     * @param context The context to access assets.
     * @param page The page number to fetch.
     * @return List of movies.
     */
    fun getMovies(context: Context, page: Int): List<Movie> {
        val fileName = "CONTENTLISTINGPAGE-PAGE$page.json"
        return try {
            val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val pageResponse = jsonAdapter.fromJson(json)
            pageResponse?.page?.contentItems?.content ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        } catch (e: com.squareup.moshi.JsonDataException) {
            e.printStackTrace()
            emptyList()
        }
    }
}
