package com.example.submisi3_made_dicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Utils.Const
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.model_view.view.*
import org.json.JSONObject

class Details : AppCompatActivity() {

    companion object{
        val DATA_MOVIE = "EXTRA_MOVIE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intentData = intent.getParcelableExtra(DATA_MOVIE) as Movie


        progressBarDetail.visibility = View.VISIBLE

        if (intentData != null){
            progressBarDetail.visibility = View.GONE
        }


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


    }
}
