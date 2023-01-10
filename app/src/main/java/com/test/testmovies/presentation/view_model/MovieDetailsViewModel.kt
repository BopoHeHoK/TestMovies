package com.test.testmovies.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.retrofit.RetrofitInstance
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.data.storage.youtube.Youtube
import com.test.testmovies.domain.model.movie_detail.MovieDetail
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsViewModel(
    val movieDatabase: MovieDatabase,
    val favoriteMovieDatabase: FavoriteMovieDatabase
) : ViewModel() {

    private var movieDetailsLiveData = MutableLiveData<MovieDetail>()
    private var movieTrailerLiveData = MutableLiveData<Youtube>()
    private var favoriteMovieLiveData =
        favoriteMovieDatabase.favoriteMovieDao().getAllFavoriteMovies()

    fun getMovieDetails(id: String?) {
        RetrofitInstance.api.getMovieDetail(id).enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                response.body()?.let {
                    movieDetailsLiveData.value = it
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("getMovieDetails", t.message.toString())
            }
        })
    }

    fun getMovieTrailer(id: String?) {
        RetrofitInstance.api.getMovieTrailer(id).enqueue(object : Callback<Youtube> {
            override fun onResponse(call: Call<Youtube>, response: Response<Youtube>) {
                response.body()?.let {
                    movieTrailerLiveData.value = it
                }
            }

            override fun onFailure(call: Call<Youtube>, t: Throwable) {
                Log.e("getMovieTrailer", t.message.toString())
            }

        })
    }

    fun observerMovieDetailLiveData(): LiveData<MovieDetail> {
        return movieDetailsLiveData
    }

    fun observerMovieTrailerLiveData(): LiveData<Youtube> {
        return movieTrailerLiveData
    }

    fun observerMovieLiveData(id: String): LiveData<List<Movie>> {
        return movieDatabase.movieDao().getMovie(id)
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