package com.test.testmovies.data.storage.model.favorite_movie

data class FavoriteMovieList(
    val errorMessage: String,
    val items: List<FavoriteMovie>
)