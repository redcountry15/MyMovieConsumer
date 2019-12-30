package com.example.submisi3_made_dicoding.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.Utils.Const
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class TvShowViewModel: ViewModel(){

    companion object{
        val listItemShow = ArrayList<TvShow>()
        val params = RequestParams()
        val const = Const()
    }

    private val listShow = MutableLiveData<ArrayList<TvShow>>()

    internal val show:LiveData<ArrayList<TvShow>>
    get() = listShow

    internal fun setShow(){

        params.put("api_key",const.API_KEY)
        params.put("language","en-US")

        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/tv/top_rated?"

        client.get(url,params,object:AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
            try {
                val result = String(responseBody)
                val response = JSONObject(result)
                val showList = response.getJSONArray("results")

                for (i in 0  until showList.length()){
                    val show = showList.getJSONObject(i)
                    val items = TvShow(show)
                    //handler
                    if(show.getString("overview") == ""){
                        items.desc = "Description isn't Available in Your Current Language "
                    }
                    listItemShow.add(items)


                }

                listShow.postValue(listItemShow)


            }catch (e:Exception){
                e.printStackTrace()
                log.d("Exception",e.message)
            }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                log.d("OnFailure",error.message)
            }

        })
    }
}