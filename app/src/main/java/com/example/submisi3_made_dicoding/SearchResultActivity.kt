package com.example.submisi3_made_dicoding

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submisi3_made_dicoding.Adapter.MovieAdapter
import com.example.submisi3_made_dicoding.Adapter.TvShowAdapter
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.ViewModel.MovieViewModel
import com.example.submisi3_made_dicoding.ViewModel.TvShowViewModel
import com.example.submisi3_made_dicoding.ui.Movie.DetailMovie
import com.example.submisi3_made_dicoding.ui.Movie.MovieFragment
import com.example.submisi3_made_dicoding.ui.TvShow.DetailShow
import com.example.submisi3_made_dicoding.ui.TvShow.ShowFragment
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.fragment_movie.*

class SearchResultActivity : AppCompatActivity() {

    private lateinit var movieViewModel:MovieViewModel
    private lateinit var showViewModel:TvShowViewModel
    private lateinit var movieAdapter:MovieAdapter
    private lateinit var showAdapter:TvShowAdapter

    private lateinit var query:String
    val EXTRA_QUERY = "EXTRA_QUERY"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        query = intent.getStringExtra(EXTRA_QUERY)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        showViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)

        val arrAdapter = ArrayAdapter<String>(
            applicationContext, android.R.layout.simple_spinner_dropdown_item,resources.getStringArray(R.array.filter_array_search)
        )

        spinner_search.adapter = arrAdapter

        //spinner listener
        spinner_search.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                progresBar.visibility = View.VISIBLE
                tv_not_found.visibility =View.GONE

                if(spinner_search.selectedItemPosition == 0){
                    searchMovie()
                }else if(spinner_search.selectedItemPosition == 1){
                    searchShow()
                }
            }

        }
    }
//movie things
    fun searchMovie(){
        movieViewModel.movies.observe(this,getMovieList)
        movieViewModel.searchMovie(query)
        tv_not_found.visibility = View.GONE
    }
    private val getMovieList =
        Observer<ArrayList<Movie>> { movie ->
            if (movie != null) {
                movieAdapter = MovieAdapter(movie)
                movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Movie) {
                        val intent = Intent(applicationContext, DetailMovie::class.java)
                        intent.putExtra(DetailMovie.DATA_TYPE, DetailMovie.MOV)
                        intent.putExtra(DetailMovie.DATA_MOVIE, data)
                        startActivity(intent)
                    }
                })

                rv_search.apply {
                    layoutManager = GridLayoutManager(applicationContext,2)
                    adapter = movieAdapter
                }
            }else{
                progresBar.visibility = View.GONE
                tv_not_found.visibility = View.VISIBLE

            }
        }

    //show boundary

    fun searchShow(){
        showViewModel.show.observe(this,getShowList)
        showViewModel.searchShow(query)
        tv_not_found.visibility = View.GONE
    }
    private val getShowList =
        Observer<ArrayList<TvShow>> { show ->
            if (show != null) {
                showAdapter = TvShowAdapter(show)
                showAdapter.setOnItemClickCallback(object :
                    TvShowAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: TvShow) {
                        val intentShow = Intent(applicationContext, DetailShow::class.java)
                        intentShow.putExtra(DetailShow.DATA_SHOW, data)
                        intentShow.putExtra(DetailShow.DATA_TYPE, DetailShow.SHOW)
                        startActivity(intentShow)
                    }
                })

                rv_search.apply {
                    layoutManager = GridLayoutManager(applicationContext,2)
                    adapter = showAdapter
                }
            }else{
                progresBar.visibility = View.GONE
                tv_not_found.visibility = View.VISIBLE
            }
        }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString("query").toString()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("query",query)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if(searchManager != null){
            val searchView = (menu?.findItem(R.id.action_search)?.actionView) as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    this@SearchResultActivity.query = query.toString()

                    progresBar.visibility = View.VISIBLE
                    Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                    if(spinner_search.selectedItemPosition == 0){
                        searchMovie()
                    }else if(spinner_search.selectedItemPosition == 1){
                        searchShow()
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false



            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}
