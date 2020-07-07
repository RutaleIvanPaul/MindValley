package com.example.channels.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.channels.R
import com.example.channels.adapters.CategoriesAdapter
import com.example.channels.adapters.ChannelsAdapter
import com.example.channels.adapters.NewEpisodesAdapter
import com.example.channels.services.DatabaseAccessHelper
import com.example.channels.services.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        ApiService.create()
    }

    val databaseAccessHelper =
        DatabaseAccessHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerviewChannels.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerviewCategories.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerviewNewEpisodes.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true
            databaseAccessHelper.populateAll(applicationContext,
                recyclerviewCategories,recyclerviewChannels,recyclerviewNewEpisodes)
            itemsswipetorefresh.isRefreshing = false
        }

        databaseAccessHelper.populateAll(applicationContext,
            recyclerviewCategories,recyclerviewChannels,recyclerviewNewEpisodes)

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}