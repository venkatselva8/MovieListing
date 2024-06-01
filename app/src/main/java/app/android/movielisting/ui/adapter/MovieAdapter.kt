package app.android.movielisting.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.android.movielisting.R
import app.android.movielisting.databinding.ItemMovieBinding
import app.android.movielisting.data.model.Movie
import app.android.movielisting.data.model.SearchResults

class MovieAdapter(private var movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var searchQuery: String = ""

    fun updateMovies(newMovies: List<Movie>) {
        val currentSize= movies.size
        movies.addAll(newMovies)
        notifyItemRangeInserted(currentSize, newMovies.size)
    }

    fun updateMoviesOnSearch(searchResults: SearchResults) {
        movies.clear()
        searchQuery = searchResults.query
        movies.addAll(searchResults.movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, searchQuery)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, query: String) {
            binding.movie = movie
            binding.query = query
            binding.executePendingBindings()
        }
    }
}
