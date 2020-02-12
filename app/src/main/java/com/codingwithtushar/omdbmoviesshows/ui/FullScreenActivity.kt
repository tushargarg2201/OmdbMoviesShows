package com.codingwithtushar.omdbmoviesshows.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.codingwithtushar.omdbmoviesshows.R
import com.codingwithtushar.omdbmoviesshows.adapter.FullScreenImageAdapter
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.utils.Constant

//private var utils: Utils? = null
private  var adapter: FullScreenImageAdapter? = null
private lateinit var viewPager: ViewPager
class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_layout)
        viewPager = findViewById<View>(R.id.pager) as ViewPager


        val search: Search? = intent.getParcelableExtra(Constant.ITEM_SELECTED)
        val arrayList = intent.getParcelableArrayListExtra<Search>("array_list") as ArrayList<Search>
        val position = intent.getIntExtra("position", 0)
        viewPager.adapter = FullScreenImageAdapter(this, arrayList);
        viewPager.currentItem = position

    }
}