package com.example.submisi3_made_dicoding.ui.Movie

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.DatabaseContract
import com.example.submisi3_made_dicoding.db.MovieHelper
import kotlinx.android.synthetic.main.activity_details.*

class DetailMovie : AppCompatActivity() {

    companion object{
        val DATA_MOVIE = "EXTRA_MOVIE"
        val MOV = "ordinary_mov"

        //type
        val DATA_TYPE = "EXTRA_TYPE"


    }
    lateinit var movie:Movie
    lateinit var movieHelper:MovieHelper
    lateinit var  type:String
    lateinit var  typeGet:String
    lateinit var  id: String
    lateinit var  title: String
    lateinit var rating: String
    lateinit var description: String
    lateinit var poster: String
    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()
        movie = intent.getParcelableExtra(DATA_MOVIE)
        typeGet = intent.getStringExtra(DATA_TYPE)

        val intentData = intent.getParcelableExtra(DATA_MOVIE) as Movie
        progressBarDetail.visibility = View.VISIBLE
        re.text = intentData.title
        received_text_description.text = intentData.desc
        received_text_rating.text = intentData.rating

        //sorry if it's redundant , idk to load into both object in 1 glide
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentData.poster)
            .into(image_poster)
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentData.poster)
            .into(image_backdrop)

        if(intentData != null){
            progressBarDetail.visibility = View.GONE
        }

        if (check()){
            img_button_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }else{
            img_button_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)

        }

        img_button_favorite.setOnClickListener{
            if (check()){
                favorMe()
            }else{
                Toast.makeText(this,getString(R.string.movieIsAdded),Toast.LENGTH_LONG).show()

            }
        }
            setData(intentData)
    }

    private fun setData(movie: Movie){
        id = movie.id
        title = movie.title
        description = movie.desc
        rating = movie.rating
        poster = movie.poster
        type = "movie"
    }


    fun favorMe() {
        val values = ContentValues()
        values.put(DatabaseContract.ObjectColumns.ID, id)
        values.put(DatabaseContract.ObjectColumns.TITLE, title)
        values.put(DatabaseContract.ObjectColumns.RATING, rating)
        values.put(DatabaseContract.ObjectColumns.DESCRIPTION, description)
        values.put(DatabaseContract.ObjectColumns.POSTER, poster)
        values.put(DatabaseContract.ObjectColumns.TYPE, type)

            img_button_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
            if (typeGet == MOV) {
                val result = movieHelper.insertMovie(values)
                if (result > 0) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.movieIsSucessfullyAdded),
                        Toast.LENGTH_LONG
                    ).show()
                    isFavorite = isFavorited()
                }
        }
            return
            //close of favor me bracket
    }


    fun isFavorited():Boolean{
            if (movieHelper.isMovieFavorited(movie.id)) {
                return true
        }
        return false
    }


    private fun check():Boolean{
        val cursor = movieHelper.queryById(movie.id)
        if(cursor.moveToFirst()) return false
        cursor.close()
        return true
    }



}
