package app.android.movielisting.ui.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import app.android.movielisting.R
import app.android.movielisting.data.model.Movie
import app.android.movielisting.data.model.SearchResults
import app.android.movielisting.viewmodel.MovieViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock

class SearchFragmentTest : KoinTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var searchResultsLiveData: MutableLiveData<SearchResults>

    @Before
    fun setUp() {
        GlobalContext.getOrNull()?.let {
            stopKoin()
        }

        MockProvider.register { mockkClass(it) }

        searchResultsLiveData = MutableLiveData()

        val testModule = module {
            viewModel { mockk<MovieViewModel>(relaxed = true) }
        }

        startKoin {
            modules(testModule)
        }

        viewModel = declareMock {
            every { searchResults } returns searchResultsLiveData
            every { clear() } just Runs
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testMoviesAvailable() {
        val typedQuery = "The Birds"
        val testMovies = listOf(
            Movie(name = "The Birds1", posterImage = "poster1.jpg"),
            Movie(name = "The Birds2", posterImage = "poster2.jpg")
        )

        searchResultsLiveData.postValue(SearchResults(query = typedQuery, movies = testMovies))

        launchFragmentInContainer<SearchFragment>()

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
            .check(matches(hasDescendant(withText("The Birds1"))))
            .check(matches(hasDescendant(withText("The Birds2"))))
    }

    @Test
    fun testNoMoviesAvailableMessage() {
        val typedQuery = "Hello"
        val expectedMessage = "No movies available for Hello"
        searchResultsLiveData.postValue(
            SearchResults(
                query = typedQuery,
                movies = listOf(),
                message = expectedMessage
            )
        )

        launchFragmentInContainer<SearchFragment>()

        onView(withId(R.id.tvSearchMessage)).check(matches(withText(expectedMessage)))
        onView(withId(R.id.tvSearchMessage)).check(matches(isDisplayed()))
    }
}

