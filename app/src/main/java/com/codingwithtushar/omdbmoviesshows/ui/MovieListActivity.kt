package com.codingwithtushar.omdbmoviesshows.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingwithtushar.omdbmoviesshows.R
import com.codingwithtushar.omdbmoviesshows.adapter.HorizontalRecyclerViewAdapter
import com.codingwithtushar.omdbmoviesshows.adapter.MovieListAdapter
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.utils.Constant
import com.codingwithtushar.omdbmoviesshows.utils.HorizontalItemDecorator
import com.codingwithtushar.omdbmoviesshows.utils.VerticalItemDecorator
import com.codingwithtushar.omdbmoviesshows.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MovieListActivity"

class MovieListActivity : AppCompatActivity(), MovieListAdapter.ListenerInterface,
    HorizontalRecyclerViewAdapter.ListenerInterface  {

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var horizontalRecyclerViewAdapter: HorizontalRecyclerViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var verticalRecyclerView: RecyclerView;
    private lateinit var horizontalRecyclerView: RecyclerView
    private lateinit var moviewViewModel: MovieViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var noSearchFoundView: TextView

    private var searchList = listOf<Search>()
    private var favoriteList = listOf<Search>()
    private var passingList = ArrayList<Search>();
    private var pageNumber = 1
    private var searchQuery = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.search_view)
        verticalRecyclerView = findViewById(R.id.recyclerview_vertical)
        horizontalRecyclerView = findViewById(R.id.recyclerview_horizontal)
        progressBar = findViewById(R.id.loading_progress_bar)
        noSearchFoundView = findViewById(R.id.no_search_found_view)
        moviewViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)


        initHorizontalRecyclerView()
        initVerticalRecyclerView()
        initSearchView()
        subscribeObservers()
    }

    private fun initSearchView() {
        showProgressBar(true)
        noSearchFoundView.visibility = View.GONE
        searchQuery = "Friends"
        moviewViewModel.startSearch(searchQuery, 1)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (!TextUtils.isEmpty(newText) && newText.length >= Constant.SEARCH_LENGTH) {
                    Handler().postDelayed({
                        searchQuery = newText
                        noSearchFoundView.visibility = View.GONE
                        moviewViewModel.startSearch(newText, 1)
                    }, 500)

                }
                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = query
                noSearchFoundView.visibility = View.GONE
                moviewViewModel.startSearch(query, 1)
                return true
            }

        })
    }

    private fun initHorizontalRecyclerView() {
        horizontalRecyclerViewAdapter = HorizontalRecyclerViewAdapter(favoriteList, this)
        val horizontalItemDecorator = HorizontalItemDecorator(0)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.apply {
            setLayoutManager(layoutManager)
            addItemDecoration(horizontalItemDecorator)
            setHasFixedSize(true)
            visibility = View.GONE
            adapter = horizontalRecyclerViewAdapter
        }
    }

    private fun initVerticalRecyclerView() {
        movieListAdapter = MovieListAdapter(searchList, this)
        val verticalItemDecorator = VerticalItemDecorator(10)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this)
        verticalRecyclerView.apply {
            addItemDecoration(verticalItemDecorator)
            adapter = movieListAdapter
        }
        verticalRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!verticalRecyclerView.canScrollVertically(1)) {
                    startNextPageQuery()
                }
            }
        })

        verticalRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    hideKeyBoard()
                }
            }
        })
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }
    private fun startNextPageQuery() {
        if (!TextUtils.isEmpty(searchQuery)) {
            showProgressBar(true)
            pageNumber++;
            noSearchFoundView.visibility = View.GONE
            moviewViewModel.startSearch(searchQuery, pageNumber)
        }
    }

    private fun subscribeObservers() {
        moviewViewModel.getSearchList().observe(this, Observer {
            searchList = it
            passingList = it as ArrayList<Search>
            movieListAdapter.apply {
                setData(it)
                notifyDataSetChanged()
            }
            showProgressBar(false)
            if (searchList.isEmpty()) {
                noSearchFoundView.visibility = View.VISIBLE
            } else {
                noSearchFoundView.visibility = View.GONE
            }
        })

        moviewViewModel.getData().observe(this, Observer {
            favoriteList = it.reversed()
            if (favoriteList.isNotEmpty()) {
                recyclerview_horizontal.visibility = View.VISIBLE
                horizontalRecyclerViewAdapter.setData(favoriteList)
                horizontalRecyclerViewAdapter.notifyDataSetChanged()
            } else {
                recyclerview_horizontal.visibility = View.GONE
            }
        })
    }

    override fun onClickEvent(position: Int) {
//        val intent = Intent(this, MovieDetailActivity::class.java)
//        intent.putExtra("favorite_selected", isFavoriteSelected(position))
//        intent.putExtra("item_selected", searchList[position])
//        startActivity(intent)

        val intent = Intent(this, FullScreenActivity::class.java)
        intent.putExtra("favorite_selected", isFavoriteSelected(position))
        intent.putExtra("item_selected", passingList[position])
        intent.putParcelableArrayListExtra("array_list", passingList)
        intent.putExtra("position", position)
        startActivity(intent)
    }


    override fun onFavoriteClick(position: Int) {
        val isUidExist = isFavoriteSelected(position)
        val searchData = searchList[position]
        if (isUidExist) {
            Toast.makeText(this, searchData.Title + " removed from the favorite list ", Toast.LENGTH_SHORT).show()
            moviewViewModel.deleteData(searchData)
        } else {
            moviewViewModel.insertData(searchData)
            Toast.makeText(this, searchData.Title + " added in the favorite list ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isFavoriteSelected(position: Int) : Boolean {
        val searchData = searchList[position]
        val imdbId = searchData.imdbID
        var isUidExist = false
        for (item in favoriteList) {
            if (item.imdbID == imdbId) {
                isUidExist = true
                break
            }
        }
        return isUidExist
    }

    private fun showProgressBar(show: Boolean) {
        if (show)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE

    }

    override fun onHorizontalCarouselClickEvent(position: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(Constant.FAVORITE_SELECTED, true)
        intent.putExtra(Constant.ITEM_SELECTED, favoriteList[position])
        startActivity(intent)
    }

    override fun onRemoveFavorite(position: Int) {
        Toast.makeText(this, favoriteList[position].Title + " removed from the favorite list ", Toast.LENGTH_SHORT).show()
        moviewViewModel.deleteData(favoriteList[position])
    }
}
