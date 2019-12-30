package com.example.submisi3_made_dicoding.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject


@Parcelize
data class Movie(
    var id:String,
    var title:String,
    var poster:String,
    var desc:String,
    var rating:String
):Parcelable{
    constructor(objek:JSONObject): this(
        objek.getString("id"),
        objek.getString("title"),
        objek.getString("poster_path"),
        objek.getString("overview"),
        objek.getString("vote_average"))

}
