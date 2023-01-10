package com.test.testmovies.di

import android.content.Context
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSortingDatabase(context: Context): SortingDatabase {
        return SortingDatabase.getInstance(context = context)
    }

    @Provides
    fun provideMovieDatabase(context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context = context)
    }

    @Provides
    fun provideFavoriteMovieDatabase(context: Context): FavoriteMovieDatabase {
        return FavoriteMovieDatabase.getInstance(context = context)
    }
}