package com.example.submisi3_made_dicoding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.Utils.Const
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import kotlinx.android.synthetic.main.activity_details.*

class DetailShow : AppCompatActivity() {

    companion object{
        val DATA_SHOW = "EXTRA_SHOW"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intentShow = intent.getParcelableExtra(DATA_SHOW) as TvShow


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



    }
}