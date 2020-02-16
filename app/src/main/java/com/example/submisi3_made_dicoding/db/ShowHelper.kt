package com.example.submisi3_made_dicoding.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.submisi3_made_dicoding.Model.Movie
import com.example.submisi3_made_dicoding.Model.TvShow
import com.example.submisi3_made_dicoding.db.DatabaseContract.ShowObjectColumns.Companion.ID
import com.example.submisi3_made_dicoding.db.DatabaseContract.ShowObjectColumns.Companion.TABLE_NAME_FAVORITE
import java.sql.SQLException

class ShowHelper(context: Context) {

    private lateinit var database: SQLiteDatabase
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)

    companion object{
        @Volatile private var INSTANCE: ShowHelper? = null
        private  const val DATABASE_TABLE = TABLE_NAME_FAVORITE

        fun getInstance(context: Context): ShowHelper {
            if(INSTANCE  == null){
                synchronized(SQLiteOpenHelper::class.java){
                    if (INSTANCE ==  null){
                        INSTANCE = ShowHelper(context)
                    }
                }
            }
            return INSTANCE as ShowHelper
        }

    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }


    fun isShowFavorited(id:String): Boolean {
       val cursor = database.query(
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
    fun queryShow(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            "$ID  ASC",
            null
        )

    }

    fun insertShow(values: ContentValues): Long{
        return  database.insert(DATABASE_TABLE,null,values)
    }

    fun getAllShow():ArrayList<TvShow>{
        val arrayList = ArrayList<TvShow>()
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
        var show: TvShow

        if(cursor.count > 0){
            do{
                show = TvShow(
                    cursor.getString(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ShowObjectColumns.TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ShowObjectColumns.POSTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ShowObjectColumns.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ShowObjectColumns.RATING))
                )
                arrayList.add(show)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }


    fun deleteShow(id:String):Int{
        return  database.delete(DATABASE_TABLE,"${ID} = ?", arrayOf(id))
    }
}