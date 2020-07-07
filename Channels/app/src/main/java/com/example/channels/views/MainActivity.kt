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

    private fun beginSearch() {
        disposable = wikiApiServe.returnCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> txt_search_result.text = "${result.data.categories.toString()} result found" },
                { error -> txt_search_result.text = "${error.message}"}
            )
    }

    private fun returnToScreen(){
        txt_search_result.text = databaseAccessHelper.fetchChannels(applicationContext).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            returnToScreen()
        }
//        databaseAccessHelper.populateCategories(applicationContext)
//        databaseAccessHelper.populateNewEpisodes(applicationContext)

        recyclerviewChannels.layoutManager = LinearLayoutManager(this)
        recyclerviewCategories.layoutManager = LinearLayoutManager(this)
        recyclerviewNewEpisodes.layoutManager = LinearLayoutManager(this)
//        recyclerview.adapter = ChannelsAdapter(this)

        databaseAccessHelper.populateAll(applicationContext,
            recyclerviewCategories,recyclerviewChannels,recyclerviewNewEpisodes)

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}