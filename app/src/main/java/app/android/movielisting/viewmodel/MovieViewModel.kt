package app.android.movielisting.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        //Reset the data if exists
        currentPage = 1
        isLastPage = false
        allMovies.clear()

        val newMovies = repository.getMovies(context, currentPage)
        allMovies.addAll(newMovies)
        _movies.value = newMovies.toMutableList()
        currentPage++
    }

    /**
     * Fetch movies on pagination
     * @param context The context to access the repository.
     */
    fun fetchMoviesOnPaginate(context:Context){
        if (isLastPage) return
        val newMovies = repository.getMovies(context, currentPage)
        if (newMovies.isEmpty()) {
            isLastPage = true
        } else {
            allMovies.addAll(newMovies)
            _movies.value = newMovies.toMutableList()
            currentPage++
        }
    }

    /**
     * Search movies by the query.
     * @param query The search query.
     */
    fun searchMovies(query: String) {
        if (query.length >= 3) {
            val filteredMovies = allMovies.filter {
                it.name.contains(query, ignoreCase = true)
            }
            _searchResults.value = SearchResults(query, filteredMovies)
        }
    }

}
