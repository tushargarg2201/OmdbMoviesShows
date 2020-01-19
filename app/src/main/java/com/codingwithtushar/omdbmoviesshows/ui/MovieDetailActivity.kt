package com.codingwithtushar.omdbmoviesshows.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codingwithtushar.omdbmoviesshows.R
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.utils.Constant
import com.codingwithtushar.omdbmoviesshows.viewmodel.MovieViewModel

class MovieDetailActivity: AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var viewModel: MovieViewModel
    private lateinit var search: Search
    private var favoriteSelected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_movie_detail)
        imageView = findViewById<ImageView>(R.id.movie_image)
        movieTitle = findViewById<TextView>(R.id.movie_title)
        movieYear = findViewById<TextView>(R.id.movie_year)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        val favoriteImageView = findViewById<ImageView>(R.id.movie_favorite);

        favoriteImageView.setOnClickListener(View.OnClickListener {
           if (favoriteSelected) {
               Toast.makeText(this, search.Title + " removed from favorite ", Toast.LENGTH_SHORT).show()
               viewModel.deleteData(search)
           } else {
               Toast.makeText(this, search.Title + " Added in favorite ", Toast.LENGTH_SHORT).show()
               viewModel.insertData(search)
           }
        })

        setData()

    }

    private fun setData() {
        var requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)

        if (intent.hasExtra(Constant.ITEM_SELECTED) || intent.hasExtra(Constant.FAVORITE_SELECTED)) {
            val search: Search? = intent.getParcelableExtra(Constant.ITEM_SELECTED)
            val favoriteSelected = intent.getBooleanExtra(Constant.FAVORITE_SELECTED, false)
            this.favoriteSelected = favoriteSelected
            search?.let {
               this.search = it
               Glide.with(this)
                   .setDefaultRequestOptions(requestOptions)
                   .load(it.Poster)
                   .into(imageView)

                movieTitle.text = it.Title
                movieYear.text = it.Year
            }
        }

    }
}