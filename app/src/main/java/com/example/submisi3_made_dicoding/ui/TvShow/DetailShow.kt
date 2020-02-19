package com.example.submisi3_made_dicoding.ui.TvShow

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.DatabaseContract
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import com.example.submisi3_made_dicoding.ui.Movie.DetailMovie
import kotlinx.android.synthetic.main.activity_details.*

class DetailShow : AppCompatActivity() {

    companion object{
        val DATA_SHOW = "EXTRA_SHOW"
        val DATA_TYPE = "TYPE"
        val SHOW = "SHOW"

    }

    lateinit var show:TvShow
    lateinit var movieHelper: MovieHelper
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

        val intentShow = intent.getParcelableExtra(DATA_SHOW) as TvShow
        typeGet = intent.getStringExtra(DATA_TYPE)
        show = intent.getParcelableExtra(DATA_SHOW)

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        progressBarDetail.visibility = View.VISIBLE
        if (intentShow != null){
            progressBarDetail.visibility = View.GONE
        }

        re.text = intentShow.title
        received_text_rating.text = intentShow.rating
        received_text_description.text = intentShow.desc

        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentShow.poster)
            .into(image_poster)
        Glide.with(applicationContext)
            .load("https://image.tmdb.org/t/p/w342/" + intentShow.poster)
            .into(image_backdrop)


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

        setData(intentShow)


    }


    private fun setData(show: TvShow){
        id = show.id
        title = show.title
        description = show.desc
        rating = show.rating
        poster = show.poster
        type = "show"
    }


    fun favorMe() {
        val values = ContentValues()
        values.put(DatabaseContract.ShowObjectColumns.ID, id)
        values.put(DatabaseContract.ShowObjectColumns.TITLE, title)
        values.put(DatabaseContract.ShowObjectColumns.RATING, rating)
        values.put(DatabaseContract.ShowObjectColumns.DESCRIPTION, description)
        values.put(DatabaseContract.ShowObjectColumns.POSTER, poster)
        values.put(DatabaseContract.ShowObjectColumns.TYPE, type)

        img_button_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
        if (typeGet == SHOW) {
            val result = movieHelper.insertMovie(values)
            if (result > 0) {
                Toast.makeText(applicationContext, getString(R.string.movieIsSucessfullyAdded), Toast.LENGTH_LONG).show()
                isFavorite = isFavorited()
            }
        }
        return
        //close of favor me bracket
    }

    fun isFavorited():Boolean{
        if (movieHelper.isMovieFavorited(show.id)) {
            return true
        }
        return false
    }

    private fun check():Boolean{
        val cursor = movieHelper.queryById(show.id)
        if(cursor.moveToFirst()) return false
        cursor.close()
        return true
    }

}