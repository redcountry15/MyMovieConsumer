package com.example.submisi3_made_dicoding.ui.Movie


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.submisi3_made_dicoding.Adapter.MovieAdapter
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
    companion object{
        private lateinit var movieAdapter: MovieAdapter
        private lateinit var movieViewModel: MovieViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MovieViewModel::class.java)
        movieViewModel.movies.observe(viewLifecycleOwner,getMovieList)
        movieViewModel.setMovie()


        progresBar2.visibility = View.VISIBLE

    }
        //get Movie List
        private val getMovieList =
            Observer<ArrayList<Movie>> { movie ->
                if(movie != null){
                    movieAdapter = MovieAdapter(movie)
                    movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: Movie) {
                              val intent = Intent(context, DetailMovie::class.java)
                               intent.putExtra(DetailMovie.DATA_TYPE, DetailMovie.MOV)
                               intent.putExtra(DetailMovie.DATA_MOVIE,data)
                            startActivity(intent)
                        }
                    })
                    rv_movie.apply {
                        layoutManager = GridLayoutManager(activity,2)
                        adapter =
                            movieAdapter
                    }
                    progresBar2.visibility = View.GONE
                }
            }
}
