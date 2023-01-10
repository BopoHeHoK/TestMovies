package com.test.testmovies.data.db.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testmovies.data.storage.model.movie.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        var INSTANCE: MovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                "movies.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MovieDatabase
        }
    }
}