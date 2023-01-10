package com.test.testmovies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.testmovies.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {

    private var binding: ActivityAppBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}