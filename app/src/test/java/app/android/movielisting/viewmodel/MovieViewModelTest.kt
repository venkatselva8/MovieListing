package app.android.movielisting.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.android.movielisting.R
import app.android.movielisting.data.model.Movie
import app.android.movielisting.data.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MovieViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: MovieRepository

    @MockK
    lateinit var context: Context

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MovieViewModel(repository)

        every { context.getString(R.string.no_movies_available, any<String>()) } returns "No movies available for Hello"
        every { context.getString(R.string.search_minimum_characters) } returns "Please enter at least 3 characters"
    }

    @Test
    fun `test fetchMovies`() {
        val movies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )
        every { repository.getMovies(context, 1) } returns movies

        viewModel.fetchMovies(context)

        assertEquals(movies, viewModel.movies.value)
    }

    @Test
    fun `test fetchMoviesOnPaginate`() {
        val initialMovies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )
        val newMovies = listOf(
            Movie("Movie 3", "poster3.jpg"),
            Movie("Movie 4", "poster4.jpg")
        )

        every { repository.getMovies(context, 1) } returns initialMovies
        every { repository.getMovies(context, 2) } returns newMovies

        viewModel.fetchMovies(context)
        viewModel.fetchMoviesOnPaginate(context)

        val expectedMovies = newMovies
        assertEquals(expectedMovies, viewModel.movies.value)
    }

    @Test
    fun `test searchMovies with valid query`() {
        val movies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )
        every { repository.getMovies(context, 1) } returns movies

        viewModel.fetchMovies(context)
        viewModel.searchMovies(context, "Movie 1")

        val searchResults = viewModel.searchResults.value
        assertEquals(1, searchResults?.movies?.size)
        assertEquals("Movie 1", searchResults?.movies?.get(0)?.name)
    }

    @Test
    fun `test searchMovies with empty query`() {
        viewModel.searchMovies(context, "")

        val searchResults = viewModel.searchResults.value
        val expectedMessage = context.getString(R.string.search_minimum_characters)
        assertEquals(expectedMessage, searchResults?.message)
    }

    @Test
    fun `test searchMovies with query having fewer than 3 characters`() {
        viewModel.searchMovies(context, "Mo")

        val searchResults = viewModel.searchResults.value
        val expectedMessage = context.getString(R.string.search_minimum_characters)
        assertEquals(expectedMessage, searchResults?.message)
    }

    @Test
    fun `test searchMovies with no results`() {
        val movies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )
        every { repository.getMovies(context, 1) } returns movies

        viewModel.fetchMovies(context)
        viewModel.searchMovies(context, "Hello")

        val searchResults = viewModel.searchResults.value
        val expectedMessage = "No movies available for Hello"
        assertEquals(expectedMessage, searchResults?.message)
    }

    @Test
    fun `test fetchMoviesOnPaginate when last page`() {
        val initialMovies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )

        every { repository.getMovies(context, 1) } returns initialMovies
        every { repository.getMovies(context, 2) } returns emptyList()

        viewModel.fetchMovies(context)
        viewModel.fetchMoviesOnPaginate(context)
        viewModel.fetchMoviesOnPaginate(context)

        val expectedMovies = initialMovies
        assertEquals(expectedMovies, viewModel.movies.value)
    }
}
