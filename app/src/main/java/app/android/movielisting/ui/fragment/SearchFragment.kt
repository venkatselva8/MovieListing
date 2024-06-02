package app.android.movielisting.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import app.android.movielisting.R
import app.android.movielisting.databinding.FragmentMovieBinding
import app.android.movielisting.ui.adapter.MovieAdapter
import app.android.movielisting.utils.ResponsiveGridLayoutManager
import app.android.movielisting.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * Fragment to display search results.
 */
class SearchFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModel<MovieViewModel>()
    private lateinit var binding: FragmentMovieBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            adapter.updateMoviesOnSearch(searchResults)
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = ResponsiveGridLayoutManager(requireContext())
    }
}
