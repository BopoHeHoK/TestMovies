package com.test.testmovies.data.storage.model.favorite_movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.testmovies.data.storage.model.movie.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovie (
    val crew: String?,
    val fullTitle: String?,
    @PrimaryKey
    val id: String,
    val imDbRating: String?,
    val imDbRatingCount: String?,
    val image: String?,
    val rank: String?,
    val rankUpDown: String?,
    val title: String?,
    val year: String?
)