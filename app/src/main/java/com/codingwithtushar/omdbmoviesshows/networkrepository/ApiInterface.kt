package com.codingwithtushar.omdbmoviesshows.networkrepository

import com.codingwithtushar.omdbmoviesshows.models.SearchResponse
import com.codingwithtushar.omdbmoviesshows.utils.Constant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/")
    fun getSearchList(
        @Query("s") query: String,
        @Query(Constant.API_KEY) apiKey: String,
        @Query(Constant.PAGE) pageNumber: Int): Single<SearchResponse>
}