package com.test.testmovies.domain.model.movie_detail

data class BoxOffice(
    val budget: String,
    val cumulativeWorldwideGross: String,
    val grossUSA: String,
    val openingWeekendUSA: String
)