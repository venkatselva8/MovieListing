package app.android.movielisting.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.android.movielisting.R
import app.android.movielisting.data.model.Movie
import app.android.movielisting.data.model.SearchResults
import app.android.movielisting.data.repository.MovieRepository

/**
 * ViewModel to handle UI-related data for movie listing.
 */
class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<MutableList<Movie>>()
    val movies: LiveData<MutableList<Movie>> get() = _movies

    private val _searchResults = MutableLiveData<SearchResults>()
    val searchResults: LiveData<SearchResults> get() = _searchResults

    private val allMovies = ArrayList<Movie>()

    private var currentPage = 1
    private var isLastPage = false

    /**
     * Fetch movies on start
     * @param context The context to access the repository.
     */
    fun fetchMovies(context: Context) {
        resetData()
        val newMovies = repository.getMovies(context, currentPage)
        addMovies(newMovies)
    }

    /**
     * Fetch movies on pagination
     * @param context The context to access the repository.
     */
    fun fetchMoviesOnPaginate(context: Context) {
        if (isLastPage) return
        val newMovies = repository.getMovies(context, currentPage)
        if (newMovies.isEmpty()) {
            isLastPage = true
        } else {
            addMovies(newMovies)
        }
    }

    /**
     * Search movies by the query.
     * @param query The search query.
     */
    fun searchMovies(context: Context, query: String) {
        if (query.length >= 3) {
            val filteredMovies = allMovies.filter {
                it.name.contains(query, ignoreCase = true)
            }
            if (filteredMovies.isEmpty()) {
                _searchResults.value = getSearchResultsByMessage(
                    context.getString(
                        R.string.no_movies_available,
                        query
                    )
                )
            } else {
                _searchResults.value = SearchResults(query, filteredMovies)
            }
        } else {
            _searchResults.value = getSearchResultsByMessage(
                context.getString(R.string.search_minimum_characters)
            )
        }
    }

    /**
     * Reset the data for a fresh fetch.
     */
    private fun resetData() {
        currentPage = 1
        isLastPage = false
        allMovies.clear()
    }

    /**
     * Add movies to the list and update the LiveData.
     * @param newMovies The list of new movies to add.
     */
    private fun addMovies(newMovies: List<Movie>) {
        allMovies.addAll(newMovies)
        _movies.value = newMovies.toMutableList()
        currentPage++
    }

    private fun getSearchResultsByMessage(searchMessage: String): SearchResults {
        return SearchResults(
            "", listOf(), searchMessage
        )
    }
}