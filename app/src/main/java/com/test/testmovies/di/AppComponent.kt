package com.test.testmovies.di

import com.test.testmovies.presentation.fragment.FavoriteMoviesFragment
import com.test.testmovies.presentation.fragment.MovieDetailsFragment
import com.test.testmovies.presentation.fragment.MoviesFragment
import com.test.testmovies.presentation.fragment.SplashFragment
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun injectSplashFragment(splashFragment: SplashFragment)

    fun injectMoviesFragment(moviesFragment: MoviesFragment)

    fun injectFavoriteMoviesFragment(favoriteMoviesFragment: FavoriteMoviesFragment)

    fun injectMovieDetailsFragment(moviesDetailsFragment: MovieDetailsFragment)
}