package com.example.submisi3_made_dicoding.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject


@Parcelize
data class TvShow (
    var id:String,
    var title:String,
    var poster:String,
    var desc:String,
    var rating: String

): Parcelable{
    constructor(objek: JSONObject): this(
        objek.getString("id"),
        objek.getString("name"),
        objek.getString("poster_path"),
        objek.getString("overview"),
        objek.getString("vote_average"))

}