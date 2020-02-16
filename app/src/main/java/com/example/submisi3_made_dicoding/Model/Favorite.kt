package com.example.submisi3_made_dicoding.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var id:String,
    var title:String,
    var poster:String,
    var desc:String,
    var rating: String,
    var type:String

):Parcelable