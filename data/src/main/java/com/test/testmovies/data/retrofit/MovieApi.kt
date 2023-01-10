package com.test.testmovies.data.retrofit

import com.test.testmovies.data.storage.model.movie.MovieList
import com.test.testmovies.data.storage.youtube.Youtube
import com.test.testmovies.domain.model.movie_detail.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("MostPopularMovies/${Api.KEY}")
    fun getMostPopularMovies(): Call<MovieList>

    @GET("Title/${Api.KEY}/{id}")
    fun getMovieDetail(@Path("id") id: String?): Call<MovieDetail>

    @GET("YouTubeTrailer/${Api.KEY}/{id}")
    fun getMovieTrailer(@Path("id") id: String?): Call<Youtube>
}