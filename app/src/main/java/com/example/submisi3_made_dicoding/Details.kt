package com.example.submisi3_made_dicoding

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Utils.Const
import com.example.submisi3_made_dicoding.db.DatabaseContract
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.model_view.view.*
import org.json.JSONObject
import java.sql.SQLException
import kotlin.math.log

class Details : AppCompatActivity() {

    companion object{
        val DATA_MOVIE = "EXTRA_MOVIE"
        val MOV = "ordinary_mov"

        //type
        val DATA_TYPE = "EXTRA_TYPE"

        //favorite
        val DATA_MOVIE_FAVORITE = "EXTRA_MOVIE_FAVORITE"
        val MOV_FAV = "fav_movie"
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
    var isFavorited = false
    var isLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        val intentData = intent.getParcelableExtra(DATA_MOVIE) as Movie
        typeGet = intent.getStringExtra(DATA_TYPE)

        if(intentData != null){
            progressBarDetail.visibility = View.GONE
        }


        when(typeGet){

            DATA_MOVIE_FAVORITE ->{
                val intentFavorite = intent.getParcelableExtra(DATA_MOVIE_FAVORITE) as Movie
                progressBarDetail.visibility = View.GONE
                re.text = intentFavorite.title
                received_text_description.text = intentFavorite.desc
                received_text_rating.text = intentFavorite.rating
                //sorry if it's redundant , idk to load into both object in 1 glide
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w342/" + intentFavorite.poster)
                    .into(image_poster)
                Glide.with(applicationContext)
                    .load("https://image.tmdb.org/t/p/w342/" + intentFavorite.poster)
                    .into(image_backdrop)

                if(intentData != null){
                    progressBarDetail.visibility = View.GONE
                }

                img_button_favorite.setOnClickListener{
                    favorMe()
                }
                isFavorited = isThisAFavorite()

            }

            MOV -> {
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
                img_button_favorite.setOnClickListener{
                    favorMe()
                }
            }
        }





        setData(intentData)
    }

    private fun setData(movie:Movie){
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
        values.put(DatabaseContract.ObjectColumns.DESCRIPTION, description)
        values.put(DatabaseContract.ObjectColumns.RATING, rating)
        values.put(DatabaseContract.ObjectColumns.POSTER, poster)
        values.put(DatabaseContract.ObjectColumns.TYPE, type)

        try {
            if (isFavorited) {
                val result = movieHelper.deleteMovie(id)
                img_button_favorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
                if (result > 0) {
                    Toast.makeText(
                        applicationContext,
                        "Favorite Berhasil Dihapus",
                        Toast.LENGTH_LONG
                    ).show()
                    isFavorited = isThisAFavorite()

                }
            } else {
                img_button_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
                val result = movieHelper.insertMovie(values)
                if (result > 0) {
                    Toast.makeText(
                        applicationContext,
                        "Favorite Berhasil Ditambahkan",
                        Toast.LENGTH_LONG
                    ).show()
                    isFavorited = isThisAFavorite()
                }

            }
            //close of favor me bracket
        }catch (e:SQLException){
           Log.d("Message" , e.message.toString())

        }

    }


    fun isThisAFavorite():Boolean{
        if(movieHelper.isMovieFavorited(id)){
            return true
        }
        return false
    }



}
