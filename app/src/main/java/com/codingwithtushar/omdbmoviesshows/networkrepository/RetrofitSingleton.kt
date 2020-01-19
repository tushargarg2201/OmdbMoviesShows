package com.codingwithtushar.omdbmoviesshows.networkrepository

import com.codingwithtushar.omdbmoviesshows.ui.MainApplication
import com.codingwithtushar.omdbmoviesshows.utils.Constant
import com.codingwithtushar.omdbmoviesshows.utils.NetworkUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitSingleton {
    companion object {

        private const val cacheSize = 5 * 1024 * 1024.toLong() // 5 MB
        private val cache = Cache(MainApplication.getContext().cacheDir, cacheSize)

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

        private val okHttpClientCache = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor{chain ->
                var request = chain.request()
                request = if (NetworkUtils.isNetworkConnected(MainApplication.getContext()))
                    // if network is connected 5 seconds cache for retrofit else 7 days cache if network is not there.
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }.build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .client(okHttpClientCache)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        private val retrofitInstance: ApiInterface = retrofit.create(ApiInterface::class.java)

        fun getRetrofitInstance(): ApiInterface {
            return retrofitInstance
        }
    }
}