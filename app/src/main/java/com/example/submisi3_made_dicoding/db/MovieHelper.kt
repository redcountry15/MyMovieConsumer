package com.example.submisi3_made_dicoding.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.R
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.DESCRIPTION
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.ID
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.POSTER
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.RATING
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.TABLE_NAME
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.TITLE
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.TYPE
import java.sql.SQLException

class MovieHelper(context:Context) {

    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database:SQLiteDatabase

    companion object{
        @Volatile private var INSTANCE: MovieHelper? = null
        private  const val DATABASE_TABLE = TABLE_NAME

        fun getInstance(context: Context): MovieHelper {
            if(INSTANCE  == null){
                synchronized(SQLiteOpenHelper::class.java){
                    if (INSTANCE ==  null){
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }

    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen())
            database.close()
    }

    fun getAllMovie():ArrayList<Movie>{
        val arrayList = ArrayList<Movie>()
        val cursor = database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            null,
            null)

        cursor.moveToFirst()
        var movie:Movie

        if(cursor.count > 0){
            do{
                movie = Movie(
                    cursor.getString(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(RATING)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                )
                arrayList.add(movie)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun isMovieFavorited(id:String):Boolean{
        val cursor =  database.query(
            DATABASE_TABLE,
            null,
            "$ID = '$id'",
           null,
            null,
            null,
            null,
            null
        )
            cursor.moveToFirst()
        if (cursor.count > 0 ){
            return true
        }
        return false
    }

    fun queryMovies(): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$TYPE = 'movie'",
            null,
            null,
            null,
            "$ID ASC",
            null
        )

    }
    //got spoiler from my friend, on the last submission there is search function, so just in case .
    fun queryById(id:String):Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertMovie(values:ContentValues): Long{
        return  database.insert(DATABASE_TABLE,null,values)
    }


    fun deleteMovie(id:String):Int{
        return  database.delete(DATABASE_TABLE,"$ID = ?", arrayOf(id))
    }
}