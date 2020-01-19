package com.codingwithtushar.omdbmoviesshows.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingwithtushar.omdbmoviesshows.databaserepository.MovieDatabase
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.networkrepository.ApiClient
import com.codingwithtushar.omdbmoviesshows.ui.MainApplication
import java.util.concurrent.Executors

class Repository(application: Application) {

    private val apiClient: ApiClient = ApiClient(application)
    private val database = MovieDatabase.getDatabaseInstance()
    private val movieDao = database.getMovieDao()
    private val executors = Executors.newSingleThreadExecutor()

    fun getSearchList(): MutableLiveData<List<Search>> {
        return apiClient.getSearchList()
    }

    fun startSearch(query: String, pageNumber: Int) {
        apiClient.startSearch(query, pageNumber)
    }

    fun insertData(search: Search) {
        executors.execute(Runnable {
            movieDao.insertData(search)
        })
    }

    fun getData(): LiveData<List<Search>> {
       return movieDao.getData()
    }

    fun deleteData(search: Search) {
        executors.execute(Runnable {
            movieDao.deleteData(search)
        })
    }
}