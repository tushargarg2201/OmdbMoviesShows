package com.codingwithtushar.omdbmoviesshows.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movietable")
data class Search (


    @ColumnInfo
    @SerializedName("Title") val Title: String,

    @ColumnInfo
    @SerializedName("Year") val Year: String,

    @PrimaryKey()
    @ColumnInfo
    @SerializedName("imdbID") val imdbID: String,

    @ColumnInfo
    @SerializedName("Type") val Type: String,

    @ColumnInfo
    @SerializedName("Poster") val Poster: String

) : Parcelable

