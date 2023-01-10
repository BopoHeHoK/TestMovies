package com.test.testmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.presentation.view_model.MovieDetailsViewModel

class MovieDetailsViewModelFactory(
    val movieDatabase: MovieDatabase,
    val favoriteMovieDatabase: FavoriteMovieDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieDatabase, favoriteMovieDatabase) as T
    }
}