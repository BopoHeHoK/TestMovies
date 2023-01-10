package com.test.testmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import com.test.testmovies.presentation.view_model.MoviesViewModel

class MoviesViewModelFactory(
    val sortingDatabase: SortingDatabase,
    val movieDatabase: MovieDatabase,
    val favoriteMovieDatabase: FavoriteMovieDatabase
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(sortingDatabase, movieDatabase, favoriteMovieDatabase) as T
    }
}
