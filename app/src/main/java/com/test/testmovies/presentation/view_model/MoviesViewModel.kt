package com.test.testmovies.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.data.storage.model.sorting.Sorting
import com.test.testmovies.presentation.constance.SortingConstance
import kotlinx.coroutines.launch

class MoviesViewModel(
    val sortingDatabase: SortingDatabase,
    val movieDatabase: MovieDatabase,
    val favoriteMovieDatabase: FavoriteMovieDatabase
) : ViewModel() {

    private var sortingListDatabase = sortingDatabase.sortingDao().getAllSorting()
    private var favoriteMovieLiveData =
        favoriteMovieDatabase.favoriteMovieDao().getAllFavoriteMovies()

    fun observerSortingMoviesLiveData(): LiveData<List<Sorting>> {
        return sortingListDatabase
    }

    fun observerMoviesLiveData(parameter: String?, search: String?): LiveData<List<Movie>> {
        if (search!!.isBlank())
            when (parameter) {
                SortingConstance.ALPHABET_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByTitle()
                SortingConstance.RATING_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByRating()
                SortingConstance.RATING_COUNT_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByRatingCount()
                else -> return movieDatabase.movieDao().getSortingMoviesByRank()
            }
        else {
            when (parameter) {
                SortingConstance.ALPHABET_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByTitleWithSearch(search)
                SortingConstance.RATING_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByRatingWithSearch(search)
                SortingConstance.RATING_COUNT_PARAMETER -> return movieDatabase.movieDao()
                    .getSortingMoviesByRatingCountWithSearch(search)
                else -> return movieDatabase.movieDao().getSortingMoviesByRankWithSearch(search)
            }
        }
    }

    fun observerFavoriteMovieLiveData(): LiveData<List<FavoriteMovie>> {
        return favoriteMovieLiveData
    }

    fun upsertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMovieDatabase.favoriteMovieDao().upsertFavoriteMovie(favoriteMovie)
        }
    }

    fun deleteFavoriteMovie(id: String) {
        viewModelScope.launch {
            favoriteMovieDatabase.favoriteMovieDao().deleteFavoriteMovie(id)
        }
    }


}