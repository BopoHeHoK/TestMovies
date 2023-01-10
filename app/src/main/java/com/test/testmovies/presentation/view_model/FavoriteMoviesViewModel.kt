package com.test.testmovies.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    val favoriteMovieDatabase: FavoriteMovieDatabase
) : ViewModel() {

    fun observerFavoriteMovieLiveData(search: String?): LiveData<List<FavoriteMovie>> {
        if (search!!.isBlank())
            return favoriteMovieDatabase.favoriteMovieDao().getAllFavoriteMovies()
        else
            return favoriteMovieDatabase.favoriteMovieDao().getAllFavoriteMoviesWithSearch(search)
    }

    fun deleteFavoriteMovie(id: String) {
        viewModelScope.launch {
            favoriteMovieDatabase.favoriteMovieDao().deleteFavoriteMovie(id)
        }
    }
}