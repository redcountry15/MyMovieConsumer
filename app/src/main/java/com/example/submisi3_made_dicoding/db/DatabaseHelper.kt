package com.example.submisi3_made_dicoding.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.submisi3_made_dicoding.db.DatabaseContract.ObjectColumns.Companion.TABLE_NAME
import com.example.submisi3_made_dicoding.db.DatabaseContract.ShowObjectColumns.Companion.TABLE_NAME_FAVORITE

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

var context = context

    companion object{
        var DATABASE_NAME = "database_katalog"
        private val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_FAVOURITE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.ObjectColumns.ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.ObjectColumns.TITLE} TEXT NOT NULL, " +
                " ${DatabaseContract.ObjectColumns.RATING} TEXT NOT NULL, " +
                " ${DatabaseContract.ObjectColumns.DESCRIPTION} TEXT NOT NULL, " +
                " ${DatabaseContract.ObjectColumns.TYPE} TEXT NOT NULL, " +
                " ${DatabaseContract.ObjectColumns.POSTER} TEXT NOT NULL) "

        private val SQL_CREATE_TABLE_SHOW = "CREATE TABLE $TABLE_NAME_FAVORITE" +
                " (${DatabaseContract.ShowObjectColumns.ID} TEXT PRIMARY KEY," +
                " ${DatabaseContract.ShowObjectColumns.TITLE} TEXT NOT NULL, " +
                " ${DatabaseContract.ShowObjectColumns.RATING} TEXT NOT NULL, " +
                " ${DatabaseContract.ShowObjectColumns.DESCRIPTION} TEXT NOT NULL, " +
                " ${DatabaseContract.ShowObjectColumns.TYPE} TEXT NOT NULL, " +
                " ${DatabaseContract.ShowObjectColumns.POSTER} TEXT NOT NULL) "


    }



    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVOURITE)
        db?.execSQL(SQL_CREATE_TABLE_SHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_FAVORITE")
        onCreate(db)
    }
}