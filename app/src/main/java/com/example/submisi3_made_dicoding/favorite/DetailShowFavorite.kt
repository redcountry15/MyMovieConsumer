package com.example.submisi3_made_dicoding.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import kotlinx.android.synthetic.main.activity_details.*

class DetailShowFavorite: AppCompatActivity() {

    companion object{
        val DATA_SHOW_FAVORITE = "DATA_SHOW_FAVORITE"
    }

    var isFavorite = false
    lateinit var showHelper: ShowHelper
    lateinit var  id:String
    lateinit var show: TvShow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        showHelper = ShowHelper.getInstance(applicationContext)
        showHelper.open()
        show = intent.getParcelableExtra(DATA_SHOW_FAVORITE)

        val intentFavorite = intent.getParcelableExtra(DATA_SHOW_FAVORITE) as TvShow
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

        val result = showHelper.deleteShow(show.id)
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
        if (showHelper.isShowFavorited(show.id)) {
            return true
        }
        return false
    }

}