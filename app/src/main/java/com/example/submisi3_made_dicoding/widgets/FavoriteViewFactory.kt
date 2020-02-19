package com.example.submisi3_made_dicoding.widgets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.submisi3_made_dicoding.Model.Favorite
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.DatabaseContract
import com.example.submisi3_made_dicoding.db.MovieHelper
import com.example.submisi3_made_dicoding.db.ShowHelper
import java.lang.Exception

internal class FavoriteViewFactory(private val context:Context):RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Movie>()
    lateinit var  movieHelper: MovieHelper

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int):Long = 0


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

        val baseUrl = "https://image.tmdb.org/t/p/w342"
        var widgetItems = mapCursorToArrayList()
        try{
            val bitmap:Bitmap = Glide.with(context)
                .asBitmap()
                .load(baseUrl + widgetItems[position].poster)
                .submit(500,500)
                .get()
            Log.d("DebugIsiFav", widgetItems[position].title)
            remoteViews.setImageViewBitmap(R.id.image_view_widget,bitmap)
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("Something Wrong" , "Exception:$e")
        }
        val intention = Intent()
        val extras = bundleOf(MyFavoriteWidget.EXTRA_ITEM to widgetItems[position],
            MyFavoriteWidget.EXTRA_NAME to widgetItems[position].title)
        intention.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.image_view_widget,intention)
        return  remoteViews
    }

    private fun mapCursorToArrayList(): ArrayList<Favorite> {
        val favHelper = MovieHelper(context)
        favHelper.open()
        val cursor = favHelper.queryAll()
        val favouritesList = ArrayList<Favorite>()
        while (cursor.moveToNext()) {
            val id =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.ID))
            val title =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.TITLE))
            val description = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    DatabaseContract.ObjectColumns.DESCRIPTION
                )
            )
            val rating =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.RATING))
            val poster =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.POSTER))
              val type =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.TYPE)
            )

            favouritesList.add(
                Favorite(
                    id,
                    title,
                    poster,
                    description,
                    rating,
                    type
                )
            )
        }
        return favouritesList
    }

    override fun getCount(): Int = mapCursorToArrayList().size


    override fun getViewTypeCount(): Int  = 1


    override fun onDestroy() {}







}