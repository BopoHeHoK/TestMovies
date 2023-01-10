package com.test.testmovies.data.storage.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
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