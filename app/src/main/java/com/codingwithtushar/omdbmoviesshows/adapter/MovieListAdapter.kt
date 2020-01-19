package com.codingwithtushar.omdbmoviesshows.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codingwithtushar.omdbmoviesshows.R
import com.codingwithtushar.omdbmoviesshows.models.Search

class MovieListAdapter(var items: List<Search>, private val listener: ListenerInterface)
    : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> () {

    private var requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

    init {
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie_list, parent, false)
        return MovieListViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(items[position].Poster)
            .into(holder.movieImageView)

        holder.movieTitle.text = items[position].Title
        holder.movieYearView.text = items[position].Year
    }

    fun setData(itemList: List<Search>) {
       items = itemList
    }

    class MovieListViewHolder(itemView: View, listener: ListenerInterface) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        var movieImageView: ImageView = itemView.findViewById(R.id.image_view)
        var movieYearView: TextView =  itemView.findViewById(R.id.movie_year)
        private val movieFavorite: ImageView = itemView.findViewById(R.id.movie_favorite)
        private val clickListener = listener

        init {
            movieImageView.setOnClickListener(this)
            movieFavorite.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.image_view) {
                clickListener.onClickEvent(adapterPosition)
            } else {
                clickListener.onFavoriteClick(adapterPosition)
            }
        }
    }

    interface ListenerInterface {
        fun onClickEvent(position: Int)
        fun onFavoriteClick(position: Int)
    }

}