package com.example.channels.views

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.BOLD_ITALIC
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.channels.R
import com.example.channels.services.ApiService
import com.example.channels.services.RequestsManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.categories_item_layout.*


class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        ApiService.create()
    }

    val requestsManager =
        RequestsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setTypeFaces()

        recyclerviewChannels.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerviewCategories.layoutManager = GridLayoutManager(this,2)
        recyclerviewNewEpisodes.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true
            requestsManager.populateAll(applicationContext,
                recyclerviewCategories,recyclerviewChannels,recyclerviewNewEpisodes)
            itemsswipetorefresh.isRefreshing = false
        }

        requestsManager.populateAll(applicationContext,
            recyclerviewCategories,recyclerviewChannels,recyclerviewNewEpisodes)

    }

//    private fun setTypeFaces(){
//            val roboto = Typeface.createFromAsset(
//        applicationContext.assets,
//        "font/Roboto-Bold.ttf"
//    )
//        main_title.setTypeface(roboto, BOLD)
//        newepisode_textview_header.setTypeface(roboto, BOLD)
//        browseCategoriesTextview.setTypeface(roboto, BOLD)
//        channel_header_textView.setTypeface(roboto, BOLD)
//        channel_subheader_textView.setTypeface(roboto)
////        category_button.setTypeface(roboto, BOLD)
//
//    }



    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}