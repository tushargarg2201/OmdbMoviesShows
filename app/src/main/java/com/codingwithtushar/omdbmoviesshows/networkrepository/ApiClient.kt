package com.codingwithtushar.omdbmoviesshows.networkrepository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.models.SearchResponse
import com.codingwithtushar.omdbmoviesshows.utils.Constant
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ApiClient(application: Application) {

    private val searchList = MutableLiveData<List<Search>>()
    private val TAG = "APIClient"

    init {
    }

    fun getSearchList(): MutableLiveData<List<Search>> {
        return searchList
    }

    fun startSearch(query: String, pageNumber: Int) {

        var olderList = listOf<Search>()
        val singleObservable: Single<SearchResponse> =
            RetrofitSingleton.getRetrofitInstance().getSearchList(query, Constant.API_KEY_VALUE, pageNumber)

        singleObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<SearchResponse> {

                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(searchResponse: SearchResponse) {
                    var currentList = searchResponse.getSearchList()
                    if (pageNumber == 1) {
                        if (currentList != null) {
                            searchList.value = currentList
                        } else {
                            searchList.value = listOf<Search>()
                        }
                    } else {
                        olderList  = searchList.value!!
                        if (currentList != null) {
                            currentList = olderList + currentList
                            searchList.setValue(currentList)
                        } else {
                            searchList.value = listOf<Search>()
                        }

                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError called" + e.message)
                    searchList.value = listOf<Search>()
                }

            })
    }


}