package com.example.channels.views

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.BOLD_ITALIC
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.channels.R
import com.example.channels.services.ApiService
import com.example.channels.services.RequestsManager
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.courses_item_layout.*


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

        channel_title_recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerviewCategories.layoutManager = GridLayoutManager(this,2)
        recyclerviewNewEpisodes.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true
            requestsManager.populateAll(applicationContext,
                recyclerviewCategories,
                recyclerviewNewEpisodes,
                channel_title_recyclerview
            )
            itemsswipetorefresh.isRefreshing = false
        }

        requestsManager.populateAll(applicationContext,
            recyclerviewCategories,
            recyclerviewNewEpisodes,
            channel_title_recyclerview
        )

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}