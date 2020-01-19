package com.codingwithtushar.omdbmoviesshows.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.repository.Repository

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = Repository(application)

    fun getSearchList(): MutableLiveData<List<Search>> {
        return repository.getSearchList()
    }

    fun startSearch(query: String, pageNumber: Int) {
        repository.startSearch(query, pageNumber)
    }

    fun insertData(search: Search) {
        repository.insertData(search)
    }

    fun getData(): LiveData<List<Search>> {
        return repository.getData()
    }

    fun deleteData(search: Search) {
        repository.deleteData(search)
    }

}