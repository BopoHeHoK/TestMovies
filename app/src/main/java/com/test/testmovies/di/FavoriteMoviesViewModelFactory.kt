package com.test.testmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.presentation.view_model.FavoriteMoviesViewModel

class FavoriteMoviesViewModelFactory(
    val favoriteMovieDatabase: FavoriteMovieDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteMoviesViewModel(favoriteMovieDatabase) as T
    }
}
