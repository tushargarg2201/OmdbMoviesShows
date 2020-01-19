package com.codingwithtushar.omdbmoviesshows.adapter

import android.content.Context
import android.util.Log
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

class HorizontalRecyclerViewAdapter(var items: List<Search>, private val listener: ListenerInterface)
    : RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ListViewHolder> () {

    private var requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

    init {
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_list_layout, parent, false)
        return ListViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(items[position].Poster)
            .into(holder.movieImageView)

        holder.movieTitle.text = items[position].Title
    }

    fun setData(itemList: List<Search>) {
        items = itemList
    }

    class ListViewHolder(itemView: View, listener: ListenerInterface) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieImageView: ImageView = itemView.findViewById(R.id.image_view)
        private val movieFavorite: ImageView = itemView.findViewById(R.id.movie_favorite)
        private val clickListener = listener

        init {
            movieImageView.setOnClickListener(this)
            movieFavorite.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.image_view) {
                clickListener.onHorizontalCarouselClickEvent(adapterPosition)
            } else {
                clickListener.onRemoveFavorite(adapterPosition)
            }
        }

    }

    interface ListenerInterface {
        fun onHorizontalCarouselClickEvent(position: Int)
        fun onRemoveFavorite(position: Int)
    }

}