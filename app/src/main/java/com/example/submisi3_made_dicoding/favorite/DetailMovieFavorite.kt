package com.example.submisi3_made_dicoding.favorite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.ui.Movie.MovieFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailMovieFavorite : AppCompatActivity() {

    companion object{
         val  DATA_MOVIE_FAVORITE = "MOVIE_FAVORITE"
    }
    var isFavorite = false
    lateinit var movieHelper:MovieHelper
    lateinit var  id:String
    lateinit var movie:Movie



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        movie = intent.getParcelableExtra(DATA_MOVIE_FAVORITE)


        val intentFavorite = intent.getParcelableExtra(DATA_MOVIE_FAVORITE) as Movie
        progressBarDetail.visibility = View.GONE
        re.text = intentFavorite.title
        received_text_description.text = intentFavorite.desc
        received_text_rating.text = intentFavorite.rating

        Log.d("hey",intentFavorite.rating)
        //sorry if it's redundant , idk to load into both object in 1 glide
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentFavorite.poster)
            .into(image_poster)
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentFavorite.poster)
            .into(image_backdrop)

        if(intentFavorite != null){
            progressBarDetail.visibility = View.GONE
        }

        img_button_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)

        img_button_favorite.setOnClickListener{
            unFavorMe()
        }


    }

    private fun unFavorMe(){
        img_button_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)

            val result = movieHelper.deleteMovie(movie.id)
            if (result > 0) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.favorite_deleted),
                    Toast.LENGTH_LONG
                ).show()
                isFavorite = isThisAFavorite()



        }
    }

    fun isThisAFavorite():Boolean{
            if (movieHelper.isMovieFavorited(movie.id)) {
                return true
            }
        return false
    }





}
