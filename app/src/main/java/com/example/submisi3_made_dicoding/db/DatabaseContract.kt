package com.example.submisi3_made_dicoding.db

import android.provider.BaseColumns

object DatabaseContract {
     internal class ObjectColumns : BaseColumns{
         companion object{
             const val TABLE_NAME = "favorite"
             const val ID = "id"
             const val TITLE = "title"
             const val RATING = "rating"
             const val DESCRIPTION = "description"
             const val POSTER ="poster"
             const val TYPE ="type"
         }
     }
}