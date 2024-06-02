package app.android.movielisting.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
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
    fun `test searchMovies`() {
        val movies = listOf(
            Movie("Movie 1", "poster1.jpg"),
            Movie("Movie 2", "poster2.jpg")
        )
        every { repository.getMovies(context, 1) } returns movies

        viewModel.fetchMovies(context)
        viewModel.searchMovies("Movie 1")

        val searchResults = viewModel.searchResults.value
        assertEquals(1, searchResults?.movies?.size)
        assertEquals("Movie 1", searchResults?.movies?.get(0)?.name)
    }
}

