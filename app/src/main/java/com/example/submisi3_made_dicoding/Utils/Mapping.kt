package com.example.submisi3_made_dicoding.Utils

import android.database.Cursor
import com.example.submisi3_made_dicoding.Model.Favorite
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.db.DatabaseContract

object Mapping {


            fun mapCursortoArrayList(cursor:Cursor): ArrayList<Favorite>{
                val favorite = ArrayList<Favorite>()
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.TITLE))
                    val poster = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.POSTER))
                    val desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.DESCRIPTION))
                    val rating = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.RATING))
                    val type =cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ObjectColumns.TYPE))


                    favorite.add(Favorite(id,title,poster,desc,rating,type))
                }

                return favorite
            }
}