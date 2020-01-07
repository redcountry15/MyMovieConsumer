package com.example.submisi3_made_dicoding.db

import android.provider.BaseColumns

internal class DatabaseContract {
     var TABLE_MOVIE = "movie"
     var TABLE_SHOW = "tv_show"

     internal class MovieColumns : BaseColumns{
         companion object{
             var ID = "id"
             var TITLE = "title"
             var SCORE = "score"
             var DESCRIPTION = "description"
             var POSTER ="poster"
             var BACKDROP = "backdrop"
         }
     }

    internal class TvShowColumns : BaseColumns{
        companion object{
            var ID = "id"
            var TITLE = "title"
            var SCORE = "score"
            var DESCRIPTION = "description"
            var POSTER ="poster"
            var BACKDROP = "backdrop"
        }
    }


}