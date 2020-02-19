package com.example.submisi3_made_dicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.submisi3_made_dicoding.Adapter.MovieAdapter
import com.example.submisi3_made_dicoding.Adapter.TvShowAdapter
import com.example.submisi3_made_dicoding.ViewModel.MovieViewModel
import com.example.submisi3_made_dicoding.ViewModel.TvShowViewModel

class SearchResultActivity : AppCompatActivity() {

    private lateinit var movieViewModel:MovieViewModel
    private lateinit var showViewModel:TvShowViewModel
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var showAdapater:TvShowAdapter

    private lateinit var query:String
    val EXTRA_QUERY = "EXTRA_QUERY"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        query = intent.getStringExtra(EXTRA_QUERY)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        showViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
