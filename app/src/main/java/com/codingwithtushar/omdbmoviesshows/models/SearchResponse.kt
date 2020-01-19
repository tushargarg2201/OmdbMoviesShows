package com.codingwithtushar.omdbmoviesshows.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResponse{

    @SerializedName("Search")
    @Expose
    private var searchList: List<Search>? = null

    fun getSearchList(): List<Search>? {
        return searchList
    }
}



