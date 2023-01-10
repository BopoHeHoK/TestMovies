package com.test.testmovies.di

import android.content.Context
import com.test.testmovies.data.db.favorite_movie.FavoriteMovieDatabase
import com.test.testmovies.data.db.movie.MovieDatabase
import com.test.testmovies.data.db.sorting.SortingDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideSplashViewModelFactory(
        sortingDatabase: SortingDatabase,
        movieDatabase: MovieDatabase
    ): SplashViewModelFactory {
        return SplashViewModelFactory(
            sortingDatabase = sortingDatabase,
            movieDatabase = movieDatabase
        )
    }

    @Provides
    fun provideMoviesViewModelFactory(
        sortingDatabase: SortingDatabase,
        movieDatabase: MovieDatabase,
        favoriteMovieDatabase: FavoriteMovieDatabase
    ): MoviesViewModelFactory {
        return MoviesViewModelFactory(
            sortingDatabase = sortingDatabase,
            movieDatabase = movieDatabase,
            favoriteMovieDatabase = favoriteMovieDatabase
        )
    }

    @Provides
    fun provideMovieDetailsViewModelFactory(
        movieDatabase: MovieDatabase,
        favoriteMovieDatabase: FavoriteMovieDatabase
    ): MovieDetailsViewModelFactory {
        return MovieDetailsViewModelFactory(
            movieDatabase = movieDatabase,
            favoriteMovieDatabase = favoriteMovieDatabase
        )
    }

    @Provides
    fun provideFavoriteMoviesViewModelFactory(
        favoriteMovieDatabase: FavoriteMovieDatabase
    ): FavoriteMoviesViewModelFactory {
        return FavoriteMoviesViewModelFactory(
            favoriteMovieDatabase = favoriteMovieDatabase
        )
    }
}