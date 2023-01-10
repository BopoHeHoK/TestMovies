package com.test.testmovies.presentation.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testmovies.R
import com.test.testmovies.app.App
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.databinding.FragmentMoviesBinding
import com.test.testmovies.di.MoviesViewModelFactory
import com.test.testmovies.presentation.adapter.MoviesAdapter
import com.test.testmovies.presentation.adapter.SortingMoviesAdapter
import com.test.testmovies.presentation.view_model.MoviesViewModel
import javax.inject.Inject

class MoviesFragment : Fragment() {

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var sortingMoviesAdapter: SortingMoviesAdapter
    private lateinit var moviesAdapter: MoviesAdapter
    private var parameter: String? = "rank"
    private var searchText: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as App).appComponent.injectMoviesFragment(moviesFragment = this)
        moviesViewModel = ViewModelProvider(
            owner = this,
            factory = moviesViewModelFactory
        )[MoviesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortingMoviesRecyclerView()
        observeSortingMovies()

        onSortingClick()

        moviesRecyclerView()
        observeMovies(parameter, searchText)

        onMovieClick()

        onFavoriteButtonClick()

        onFavoriteMoviesClick(view)
        onSearchClick(view)
    }

    private fun onSortingClick() {
        sortingMoviesAdapter.onItemClick = {
            observeSortingMovies()
            parameter = it.parameter
            observeMovies(parameter, searchText)
        }
    }

    private fun onMovieClick() {
        moviesAdapter.onItemClick = { movie ->
            findNavController().navigate(
                R.id.action_moviesFragment_to_movieDetailsFragment,
                Bundle().apply {
                    putString("id", movie.id)
                })
        }
    }

    private fun onFavoriteButtonClick() {
        moviesAdapter.onItemFavoriteClick = { movie ->
            var mFavoriteMovie: FavoriteMovie? = null
            moviesViewModel.observerFavoriteMovieLiveData()
                .observe(viewLifecycleOwner, Observer { favoriteMovies ->
                    favoriteMovies.forEach { favoriteMovie ->
                        if (favoriteMovie.id == movie.id) {
                            mFavoriteMovie = favoriteMovie
                        }
                    }
                })
            if (mFavoriteMovie != null) {
                moviesViewModel.deleteFavoriteMovie(movie.id)
            } else {
                val favoriteMovie = FavoriteMovie(
                    crew = movie.crew,
                    fullTitle = movie.fullTitle,
                    id = movie.id,
                    imDbRating = movie.imDbRating,
                    imDbRatingCount = movie.imDbRatingCount,
                    image = movie.image,
                    rank = movie.rank,
                    rankUpDown = movie.rankUpDown,
                    title = movie.title,
                    year = movie.year
                )
                favoriteMovie.let {
                    moviesViewModel.upsertFavoriteMovie(it)
                }
            }
        }
    }

    private fun sortingMoviesRecyclerView() {
        sortingMoviesAdapter = SortingMoviesAdapter()
        binding.rvSorting.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = sortingMoviesAdapter
        }
    }

    private fun observeSortingMovies() {
        moviesViewModel.observerSortingMoviesLiveData()
            .observe(viewLifecycleOwner, Observer { sorting ->
                sortingMoviesAdapter.setSortingMoviesList(sorting)
            })
    }

    private fun moviesRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
    }

    private fun observeMovies(parameter: String?, search: String?) {
        moviesViewModel.observerMoviesLiveData(parameter, search)
            .observe(viewLifecycleOwner, object : Observer<List<Movie>> {
                override fun onChanged(t: List<Movie>?) {
                    moviesViewModel.observerFavoriteMovieLiveData()
                        .observe(viewLifecycleOwner, Observer { favoriteMovies ->
                            val favoriteMoviePosition = ArrayList<Int>()
                            var moviePos = -1
                            var i = 0
                            t?.forEach { movie ->
                                favoriteMovies.forEach { favoriteMovie ->
                                    if (favoriteMovie.id == movie.id) {
                                        moviePos = i
                                        favoriteMoviePosition.add(moviePos)
                                    }
                                }
                                i++
                            }
                            if (t != null) {
                                moviesAdapter.setMoviesList(t, favoriteMoviePosition)
                            }
                        })
                }
            })
    }

    private fun onSearchClick(view: View) {
        binding.apply {
            btnSearch.setOnClickListener {
                searchText = binding.etSearch.text.toString()
                observeMovies(parameter, searchText)
            }
            etSearch.setOnKeyListener(OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    searchText = binding.etSearch.text.toString()
                    observeMovies(parameter, searchText)
                    return@OnKeyListener true
                }
                false
            })
        }
    }


    private fun onFavoriteMoviesClick(view: View) {
        moviesViewModel.observerFavoriteMovieLiveData()
            .observe(viewLifecycleOwner, Observer { favoriteMovies ->
                binding.apply {
                    if (favoriteMovies.size > 0) {
                        btnToFavorites.visibility = View.VISIBLE
                        btnToFavorites.setOnClickListener {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_moviesFragment_to_favoriteMoviesFragment);
                        }
                    } else {
                        btnToFavorites.visibility = View.INVISIBLE
                    }
                }
            })
    }
}