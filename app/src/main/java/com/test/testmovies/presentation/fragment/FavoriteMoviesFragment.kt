package com.test.testmovies.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.test.testmovies.R
import com.test.testmovies.app.App
import com.test.testmovies.databinding.FragmentFavoriteMoviesBinding
import com.test.testmovies.di.FavoriteMoviesViewModelFactory
import com.test.testmovies.presentation.adapter.FavoriteMoviesAdapter
import com.test.testmovies.presentation.view_model.FavoriteMoviesViewModel
import javax.inject.Inject

class FavoriteMoviesFragment : Fragment() {

    @Inject
    lateinit var favoriteMoviesViewModelFactory: FavoriteMoviesViewModelFactory

    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel
    private lateinit var moviesAdapter: FavoriteMoviesAdapter
    private var searchText: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as App).appComponent.injectFavoriteMoviesFragment(
            favoriteMoviesFragment = this
        )
        favoriteMoviesViewModel = ViewModelProvider(
            owner = this,
            factory = favoriteMoviesViewModelFactory
        )[FavoriteMoviesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteMoviesRecyclerView()
        observeFavoriteMovies(searchText)

        onFavoriteMoviesClick()

        onFavoriteButtonClick()

        onSearchClick(view)

        onClearAllButtonClick()
    }

    private fun onFavoriteButtonClick() {
        moviesAdapter.onItemFavoriteClick = { favoriteMovie ->
            favoriteMoviesViewModel.deleteFavoriteMovie(favoriteMovie.id)
        }
    }

    private fun onClearAllButtonClick() {
        favoriteMoviesViewModel.observerFavoriteMovieLiveData(searchText)
            .observe(viewLifecycleOwner, Observer { favoriteMovies ->
                binding.apply {
                    if (favoriteMovies.size > 0) {
                        btnClearAll.visibility = View.VISIBLE
                        tvListIsEmpty.visibility = View.INVISIBLE
                        btnClearAll.setOnClickListener {
                            favoriteMovies.forEach { favoriteMovie ->
                                favoriteMoviesViewModel.deleteFavoriteMovie(favoriteMovie.id)
                            }
                        }
                    } else {
                        btnClearAll.visibility = View.INVISIBLE
                        tvListIsEmpty.visibility = View.VISIBLE
                    }
                }
            })
    }

    private fun onFavoriteMoviesClick() {
        moviesAdapter.onItemClick = { movie ->
            findNavController().navigate(
                R.id.action_favoriteMoviesFragment_to_movieDetailsFragment,
                Bundle().apply {
                    putString("id", movie.id)
                })
        }
    }

    private fun favoriteMoviesRecyclerView() {
        moviesAdapter = FavoriteMoviesAdapter()
        binding.rvFavoriteMovies.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
        }
    }

    private fun onSearchClick(view: View) {
        binding.apply {
            btnSearch.setOnClickListener {
                searchText = binding.etSearch.text.toString()
                observeFavoriteMovies(searchText)
            }
            etSearch.setOnKeyListener(OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    searchText = binding.etSearch.text.toString()
                    observeFavoriteMovies(searchText)
                    return@OnKeyListener true
                }
                false
            })
        }
    }

    private fun observeFavoriteMovies(search: String?) {
        favoriteMoviesViewModel.observerFavoriteMovieLiveData(search)
            .observe(viewLifecycleOwner, Observer { movies ->
                moviesAdapter.setMoviesList(movies)
            })
    }
}