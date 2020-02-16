package com.example.submisi3_made_dicoding.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submisi3_made_dicoding.BuildConfig
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Utils.Const
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MovieViewModel: ViewModel() {
    companion object{
        val listItemMovie = ArrayList<Movie>()
        val params = RequestParams()
        val const = Const()
    }

    private val listMovie = MutableLiveData<ArrayList<Movie>>()

    internal val movies : LiveData<ArrayList<Movie>>
    get() = listMovie

    internal fun setMovie(){


        params.put("api_key", BuildConfig.API_KEY)
        params.put("language","en-US")

        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/movie?"

        client.get(url,params,object :AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val response = JSONObject(result)
                    val movieList = response.getJSONArray("results")

                    for(i in 0 until movieList.length()){

                        val movie = movieList.getJSONObject(i)
                        val items = Movie(movie)
                        //handler
                        if (movie.getString("overview") == "" ){
                            items.desc = " Description isn't Available in Your Current Language  "
                        }
                        listItemMovie.add(items)

                    }

                    listMovie.postValue(listItemMovie)


                }catch (e:Exception){
                    e.printStackTrace()
                    Log.d("Exception",e.message!!)
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("OnFailure", error.message!!)
            }

        })

    }



}