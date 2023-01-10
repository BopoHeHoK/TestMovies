package com.test.testmovies.data.db.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.testmovies.data.storage.model.movie.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: String): LiveData<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY rank DESC")
    fun getSortingMoviesByRank(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getSortingMoviesByTitle(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY imDbRating DESC")
    fun getSortingMoviesByRating(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY imDbRatingCount DESC")
    fun getSortingMoviesByRatingCount(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE title LIKE  '%' || :search || '%' ORDER BY rank DESC")
    fun getSortingMoviesByRankWithSearch(search: String?): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE title LIKE  '%' || :search || '%' ORDER BY title ASC")
    fun getSortingMoviesByTitleWithSearch(search: String?): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE title LIKE  '%' || :search || '%' ORDER BY imDbRating DESC")
    fun getSortingMoviesByRatingWithSearch(search: String?): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE title LIKE  '%' || :search || '%' ORDER BY imDbRatingCount DESC")
    fun getSortingMoviesByRatingCountWithSearch(search: String?): LiveData<List<Movie>>
}