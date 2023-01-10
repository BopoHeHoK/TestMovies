package com.test.testmovies.data.db.favorite_movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMovieDatabase: RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        @Volatile
        var INSTANCE: FavoriteMovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FavoriteMovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    FavoriteMovieDatabase::class.java,
                "favorite_movies.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as FavoriteMovieDatabase
        }
    }
}