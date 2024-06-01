package app.android.movielisting.di

import app.android.movielisting.data.repository.MovieRepository
import app.android.movielisting.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MovieViewModel(get()) }
    single { MovieRepository() }
}