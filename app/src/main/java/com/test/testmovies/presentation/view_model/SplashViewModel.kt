package com.test.testmovies.presentation.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import com.test.testmovies.data.retrofit.RetrofitInstance
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.data.storage.model.movie.MovieList
import com.test.testmovies.data.storage.model.sorting.Sorting
import com.test.testmovies.presentation.constance.SortingConstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel(
    private val sortingDatabase: SortingDatabase,
    private val movieDatabase: MovieDatabase
) : ViewModel() {


    private var sortingMoviesLiveData = MutableLiveData<ArrayList<Sorting>>()
    private var sortingList = ArrayList<Sorting>()
    private var movieLiveData = MutableLiveData<List<Movie>>()

    fun setSorting() {
        sortingList.add(
            Sorting(1, SortingConstance.RANK_VALUE, SortingConstance.RANK_PARAMETER),
        )
        sortingList.add(
            Sorting(2, SortingConstance.ALPHABET_VALUE, SortingConstance.ALPHABET_PARAMETER),
        )
        sortingList.add(
            Sorting(3, SortingConstance.RATING_VALUE, SortingConstance.RATING_PARAMETER),
        )
        sortingList.add(
            Sorting(
                4,
                SortingConstance.RATING_COUNT_VALUE,
                SortingConstance.RATING_COUNT_PARAMETER
            ),
        )
        sortingMoviesLiveData.value = sortingList
    }

    fun getMovieItems() {
        RetrofitInstance.api.getMostPopularMovies().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                response.body()?.let { movieList ->
                    movieLiveData.postValue(movieList.items)
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("getMovieItems", t.message.toString())
            }

        })
    }

    fun observerSortingLiveData(): MutableLiveData<ArrayList<Sorting>> {
        return sortingMoviesLiveData
    }

    fun upsertSorting(sorting: Sorting) {
        viewModelScope.launch {
            sortingDatabase.sortingDao().upsertSorting(sorting)
        }
    }

    fun observerMovieLiveData(): MutableLiveData<List<Movie>> {
        return movieLiveData
    }

    fun upserMovie(movie: Movie) {
        viewModelScope.launch {
            movieDatabase.movieDao().upsertMovie(movie)
        }
    }
}