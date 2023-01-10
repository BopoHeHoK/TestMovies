package com.test.testmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import com.test.testmovies.presentation.view_model.SplashViewModel

class SplashViewModelFactory(
    val sortingDatabase: SortingDatabase,
    val movieDatabase: MovieDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(sortingDatabase, movieDatabase) as T
    }
}
