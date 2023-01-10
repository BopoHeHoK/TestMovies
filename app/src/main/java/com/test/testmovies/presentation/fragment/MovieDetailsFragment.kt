package com.test.testmovies.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.test.testmovies.R
import com.test.testmovies.app.App
import com.test.testmovies.data.storage.model.favorite_movie.FavoriteMovie
import com.test.testmovies.data.storage.youtube.Youtube
import com.test.testmovies.databinding.FragmentMovieDetailsBinding
import com.test.testmovies.di.MovieDetailsViewModelFactory
import com.test.testmovies.domain.model.movie_detail.MovieDetail
import com.test.testmovies.presentation.adapter.ActorAdapter
import com.test.testmovies.presentation.view_model.MovieDetailsViewModel
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var movieDetailsViewModelFactory: MovieDetailsViewModelFactory

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var actorAdapter: ActorAdapter
    private var id: String? = null
    private lateinit var movieToSave: FavoriteMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as App).appComponent.injectMovieDetailsFragment(
            moviesDetailsFragment = this
        )
        movieDetailsViewModel = ViewModelProvider(
            owner = this,
            factory = movieDetailsViewModelFactory
        )[MovieDetailsViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        id = requireArguments().getString("id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingCase()

        movieDetailsViewModel.getMovieDetails(id)
        movieDetailsViewModel.getMovieTrailer(id)

        observeMovieDetails()
        actorRecyclerView()
        observeMovieTrailer()
    }

    private fun loadingCase() {
        binding.apply {
            binding.progressBar.visibility = View.VISIBLE
            btnFavorites.visibility = View.INVISIBLE
            tvTitle.visibility = View.INVISIBLE
            tvRating.visibility = View.INVISIBLE
            tvDate.visibility = View.INVISIBLE
            tvDescTitle.visibility = View.INVISIBLE
            tvDesc.visibility = View.INVISIBLE
            tvActors.visibility = View.INVISIBLE
            imgYoutube.visibility = View.INVISIBLE
        }
    }

    private fun observeMovieTrailer() {
        movieDetailsViewModel.observerMovieTrailerLiveData()
            .observe(viewLifecycleOwner, Observer<Youtube> { movie ->
                binding.apply {
                    imgYoutube.setOnClickListener {
                        val url = movie.videoUrl
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(url))
                        startActivity(intent)
                        imgYoutube.setEnabled(false)
                        Thread.sleep(2000)
                        imgYoutube.setEnabled(true)
                    }
                }
            })
    }

    private fun actorRecyclerView() {
        actorAdapter = ActorAdapter()
        binding.rvActors.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = actorAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeMovieDetails() {
        movieDetailsViewModel.observerMovieDetailLiveData()
            .observe(viewLifecycleOwner, Observer<MovieDetail> { movie ->
                if (movie.id != null) {
                    binding.apply {
                        activity?.let {
                            Glide.with(it.applicationContext).load(movie.image).into(imgMovieDetail)
                        }
                        tvTitle.text = movie.title
                        tvRating.text = "Рейтинг: ${movie.imDbRating}"
                        tvDate.text = "Дата выхода: ${movie.releaseDate}"
                        tvDesc.text = movie.plot
                        actorAdapter.setActorList(movie.actorList)

                        movieDetailsViewModel.observerFavoriteMovieLiveData()
                            .observe(viewLifecycleOwner, Observer { favoriteMovies ->
                                var mFavoriteMovie: FavoriteMovie? = null
                                favoriteMovies?.forEach { favoriteMovie ->
                                    if (favoriteMovie.id == movie.id) {
                                        mFavoriteMovie = favoriteMovie
                                    }
                                }
                                if (mFavoriteMovie != null) {
                                    btnFavorites.setImageResource(R.drawable.ic_favorite_remove)
                                    btnFavorites.setOnClickListener {
                                        movieDetailsViewModel.deleteFavoriteMovie(movie.id)
                                        btnFavorites.setImageResource(R.drawable.ic_favorite_add)
                                    }
                                } else {
                                    movieDetailsViewModel.observerMovieLiveData(movie.id)
                                        .observe(viewLifecycleOwner, Observer { moviesToSave ->
                                            btnFavorites.setBackgroundResource(R.drawable.ic_favorite_add)
                                            btnFavorites.setOnClickListener {
                                                moviesToSave.forEach {
                                                    movieToSave = FavoriteMovie(
                                                        crew = it.crew,
                                                        fullTitle = it.fullTitle,
                                                        id = it.id,
                                                        imDbRating = it.imDbRating,
                                                        imDbRatingCount = it.imDbRatingCount,
                                                        image = it.image,
                                                        rank = it.rank,
                                                        rankUpDown = it.rankUpDown,
                                                        title = it.title,
                                                        year = it.year
                                                    )
                                                }
                                                movieToSave.let {
                                                    movieDetailsViewModel.upsertFavoriteMovie(it)
                                                    btnFavorites.setImageResource(R.drawable.ic_favorite_remove)
                                                }
                                            }
                                        })
                                }
                            })
                        onResponseCase()
                    }
                }
            })
    }

    private fun onResponseCase() {
        binding.apply {
            binding.progressBar.visibility = View.INVISIBLE
            btnFavorites.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvRating.visibility = View.VISIBLE
            tvDate.visibility = View.VISIBLE
            tvDescTitle.visibility = View.VISIBLE
            tvDesc.visibility = View.VISIBLE
            tvActors.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
        }
    }
}