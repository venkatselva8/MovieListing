package app.android.movielisting.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import app.android.movielisting.R
import app.android.movielisting.ui.fragment.ListingFragment
import app.android.movielisting.ui.fragment.SearchFragment
import app.android.movielisting.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()
    private lateinit var searchMenuItem: MenuItem
    private lateinit var searchView: SearchView
    private val searchFragmentTag = "SearchFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.toolbar_title)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, ListingFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length >= 3) {
                    viewModel.searchMovies(newText)
                    if (supportFragmentManager.findFragmentByTag(searchFragmentTag) == null) {
                        supportFragmentManager.commit {
                            replace(
                                R.id.fragment_container,
                                SearchFragment(),
                                searchFragmentTag
                            )
                            addToBackStack(null)
                        }
                    }
                } else {
                    supportFragmentManager.popBackStack()
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            resetToolbar()
            false
        }

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                resetToolbar()
                return true
            }
        })

        return true
    }

    fun resetToolbar() {
        supportActionBar?.title = getString(R.string.toolbar_title)
        invalidateOptionsMenu()
        supportFragmentManager.popBackStack()
    }
}