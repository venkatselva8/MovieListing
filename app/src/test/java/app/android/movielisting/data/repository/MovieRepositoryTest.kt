package app.android.movielisting.data.repository

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.IOException

class MovieRepositoryTest {

    @MockK
    lateinit var context: Context

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = MovieRepository()
    }

    @Test
    fun `test getMovies with valid data`() {
        val page = 1
        val json = """
            {
                "page": {
                    "title": "title",
                    "total-content-items": "20",
                    "page-num": "1",
                    "page-size": "20",
                    "content-items": {
                        "content": [
                            { "name": "Movie 1", "poster-image": "poster1.jpg" },
                            { "name": "Movie 2", "poster-image": "poster2.jpg" }
                        ]
                    }
                }
            }
        """.trimIndent()
        val inputStream = ByteArrayInputStream(json.toByteArray())
        every { context.assets.open("CONTENTLISTINGPAGE-PAGE$page.json") } returns inputStream

        val movies = repository.getMovies(context, page)

        assertEquals(2, movies.size)
        assertEquals("Movie 1", movies[0].name)
        assertEquals("poster1.jpg", movies[0].posterImage)
        assertEquals("Movie 2", movies[1].name)
        assertEquals("poster2.jpg", movies[1].posterImage)
    }

    @Test
    fun `test getMovies with invalid data`() {
        val page = 1
        every { context.assets.open("CONTENTLISTINGPAGE-PAGE$page.json") } throws IOException("File not found")

        val movies = repository.getMovies(context, page)

        assertEquals(0, movies.size)
    }
}
