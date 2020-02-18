package com.example.submisi3_made_dicoding.widgets

import android.content.Context
import android.graphics.Bitmap
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Favorite
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import java.lang.Exception

internal class FavoriteViewFactory(private val context:Context):RemoteViewsService.RemoteViewsFactory {
    private var bipmap: Bitmap? = null

    override fun onCreate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLoadingView(): RemoteViews? = null


    override fun getItemId(position: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataSetChanged() {
        val identifyToken = Binder.clearCallingIdentity()
        Binder.restoreCallingIdentity(identifyToken)
    }

    override fun hasStableIds(): Boolean =false

    @RequiresApi
    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.item_widget
        )

        val baseUrl = "https://image.tmdb.org/t/p/w500"
        var widgetItems = mapCursorToArrayList()

        try{
            val bitmap:Bitmap = Glide.with(context)
                .asBitmap()
                .load(baseUrl + widgetItems[position].poster)
                .submit(500,500)
                .get()
            remoteViews.setImageViewBitmap(R.id.image_view_widget,bitmap)
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("Something Wrong" , "Exception:$e")
        }
        return  remoteViews
    }

    private  fun mapCursorToArrayList():ArrayList<Movie>{
        val movieHelper = MovieHelper(context)
        movieHelper.open()
        val movieList = ArrayList<Movie>()
        val geAll = movieHelper.getAllMovie()
        movieList.addAll(geAll)
        return movieList
    }

    private  fun showMapCursorToArrayList():ArrayList<TvShow>{
        val showHelper = ShowHelper(context)
        showHelper.open()
        val showList = ArrayList<TvShow>()
        val geAll = showHelper.getAllShow()
        showList.addAll(geAll)
        return showList
    }

    override fun getCount(): Int = mapCursorToArrayList().size


    override fun getViewTypeCount(): Int  = 1


    override fun onDestroy() {}

}