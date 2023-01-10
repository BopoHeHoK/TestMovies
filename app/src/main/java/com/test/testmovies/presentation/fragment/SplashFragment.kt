package com.test.testmovies.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.test.testmovies.R
import com.test.testmovies.app.App
import com.test.testmovies.data.storage.model.movie.Movie
import com.test.testmovies.databinding.FragmentSplashBinding
import com.test.testmovies.data.storage.model.sorting.Sorting
import com.test.testmovies.di.SplashViewModelFactory
import com.test.testmovies.presentation.view_model.SplashViewModel
import javax.inject.Inject

class SplashFragment : Fragment() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    private lateinit var binding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel
    private var sortingToSave: ArrayList<Sorting>? = null
    private var movieToSave: List<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as App).appComponent.injectSplashFragment(splashFragment = this)
        splashViewModel =
            ViewModelProvider(this, splashViewModelFactory)[SplashViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAnim()

        splashViewModel.setSorting()
        observeSorting()

        createDatabase()

        splashViewModel.getMovieItems()
        observeMovie()

        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(view)
                .navigate(R.id.action_splashFragment_to_moviesFragment);
        }, 3000)
    }

    private fun addAnim() {
        val rotate = AnimationUtils.loadAnimation(context, R.anim.loading_rotate)
        binding.imgLoading.animation = rotate
    }

    private fun observeSorting() {
        splashViewModel.observerSortingLiveData()
            .observe(viewLifecycleOwner, object : Observer<ArrayList<Sorting>> {
                override fun onChanged(t: ArrayList<Sorting>?) {
                    sortingToSave = t
                }
            })
    }

    private fun createDatabase() {
        Handler(Looper.getMainLooper()).postDelayed({
            sortingToSave?.forEach { sorting ->
                sorting.let {
                    splashViewModel.upsertSorting(it)
                }
            }
            movieToSave?.forEach { movie ->
                movie.let {
                    splashViewModel.upserMovie(it)
                }
            }
        }, 1000)
    }

    private fun observeMovie() {
        splashViewModel.observerMovieLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<Movie>> {
                override fun onChanged(t: List<Movie>?) {
                    movieToSave = t
                }
            })
    }
}