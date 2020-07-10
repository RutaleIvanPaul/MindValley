package com.example.channels.services

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.adapters.CategoriesAdapter
import com.example.channels.adapters.ChannelsAdapter
import com.example.channels.adapters.CoursesAdapter
import com.example.channels.adapters.NewEpisodesAdapter
import com.example.channels.database.*
import com.example.channels.models.*
import com.example.channels.views.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.channel_title_recyclerview.*
import kotlinx.android.synthetic.main.newepisodes_layout.*
import org.jetbrains.anko.db.classParser

class RequestsManager {
    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        ApiService.create()
    }

//    val roboto = Typeface.createFromAsset(
//        applicationContext.assets,
//        "font/Roboto-Bold.ttf"
//    )

    private fun checkNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }

    private fun noNetWorkNoData(context: Context) {
        Toast.makeText(
            context,
            "No Internet Connection and No Data in Database to support Offline Mode",
            Toast.LENGTH_LONG
        ).show()
    }

    fun populateAll(
        context: Context,
        recyclerViewCategories: RecyclerView,
        recyclerViewNewEpisode: RecyclerView,
        channel_title_recyclerview: RecyclerView
    ){

        val isConnected: Boolean = checkNetwork(context)

        if (isConnected){
            populateCategoriesFromApi(recyclerViewCategories,context)
            populateChannelsFromApi(context,
                channel_title_recyclerview
            )
            populateNewEpisodesFromApi(recyclerViewNewEpisode,context)
        }
        else{
            populateCategoriesFromDatabase(recyclerViewCategories,context)
            populateChannelsFromDatabase(context, channel_title_recyclerview)
            populateNewEpisodesFromDatabase(recyclerViewNewEpisode,context)
        }


    }

    private fun populateCategoriesFromApi(recyclerView: RecyclerView,context: Context) {
        disposable = wikiApiServe.returnCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val categories = result.data.categories
                    recyclerView.adapter = CategoriesAdapter(categoriesFromApi = categories,
                        context = context)
                    clearCategories(context)
                    for (item in categories) {
                        saveCategory(
                            context,
                            item.name
                        )
                    }
                },
                { error ->
                    Log.d("API ERROR", "API Fetch Error: ${error.message} ")
                }
            )
    }

    private fun populateCategoriesFromDatabase(
        recyclerView: RecyclerView,
        context: Context) {
        try {
            recyclerView.adapter = CategoriesAdapter(
                categoriesFromDB = fetchCategories(context), context = context)
        }
        catch (e: Exception){
            e.printStackTrace()
            noNetWorkNoData(context)
        }
    }

    fun fetchCategories(context: Context):List<DatabaseCategory>{
        return returnCategory(context).parseList(classParser<DatabaseCategory>())
    }

    fun clearCategories(context: Context){
        clearCategory(context)
    }

    fun populateNewEpisodesFromApi(recyclerView: RecyclerView,context: Context){
        disposable = wikiApiServe.returnNewEpisodes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val episodes = result.data.media.subList(0,6)
                    recyclerView.adapter = NewEpisodesAdapter(
                        newEpisodesFromApi = episodes, context = context
                    )
                    clearNewEpisodes(context)
                    for (item in 0 .. 5 ) {
                        saveNewEpisode(
                            context, episodes[item].channel.title, episodes[item].coverAsset.url,
                            episodes[item].title, episodes[item].type
                        )
                    }
                },
                { error -> Log.d("API ERROR","API Fetch Error: ${error.message} ")}
            )
    }

    private fun populateNewEpisodesFromDatabase(
        recyclerView: RecyclerView,
        context: Context) {
        try {
            recyclerView.adapter = NewEpisodesAdapter(
                newEpisodesFromDB = fetchNewEpisodes(context), context = context)
        }
        catch (e: Exception){
            e.printStackTrace()
            noNetWorkNoData(context)
        }
    }

    fun fetchNewEpisodes(context: Context):List<DatabaseNewEpisode>{
        return returnNewEpisode(context).parseList(classParser<DatabaseNewEpisode>()).subList(0,6)
    }

    fun clearNewEpisodes(context: Context){
        clearNewEpisode(context)
    }

    private fun populateChannelsFromDatabase(
        context: Context,
        channel_title_recyclerview: RecyclerView
    ) {
        try {
            channel_title_recyclerview.adapter = ChannelsAdapter(
                channelsFromDB = fetchChannels(context), context = context)
        }
        catch (e: Exception){
            e.printStackTrace()
            noNetWorkNoData(context)
        }
    }

    private fun populateChannelsFromApi(
        context: Context,
        channel_title_recyclerview: RecyclerView
    ) {
        disposable = wikiApiServe.returnChannels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val channels: List<ChannelsModel.Channel> = result.data.channels
                    channel_title_recyclerview.adapter = ChannelsAdapter(
                        channelsFromApi = channels, context = context
                    )

//                    for (channel in channels){
//                        val recyclerView = RecyclerView(context)
//                        recyclerView.layoutManager = LinearLayoutManager(context,
//                            LinearLayoutManager.HORIZONTAL,false)
//                        recyclerView.adapter = CoursesAdapter(latestCourseMediaFromApi = channel.latestMedia,context = context)
//                        channels_layout.addView(recyclerView)
//                    }
                    clearChannels(context)
                    clearLatestMedias(context)
                    for (channel in channels) {
                        saveChannel(
                            context,
                            channel.coverAsset.url ?: " ",
                            channel.iconAsset.thumbnailUrl ?: " ",
                            channel.iconAsset.url ?: " ",
                            channel.id ?: " ",
                            channel.mediaCount ?: 0,
                            channel.slug ?: " ",
                            channel.title ?: " ",
                            channel.series.isNotEmpty().toString()
                        )

                        for (latestmedia in channel.latestMedia){
                            saveLatestMedia(
                                context,
                                channel.title ?: " ",
                                latestmedia.coverAsset.url ?: " ",
                                latestmedia.title,
                                latestmedia.type
                            )
                        }
                    }

                },
                { error -> Log.d("API ERROR", "API Fetch Error: ${error.message} ") }
            )
    }

    fun fetchChannels(context: Context):List<DatabaseChannel>{
        return returnChannel(context).parseList(classParser<DatabaseChannel>()).subList(0,6)
    }

    fun fetchMedia(context: Context, channel_name: String): List<DatabaseMedia>{
        val returnedMedia = returnMedia(context,channel_name).parseList(classParser<DatabaseMedia>())
        if (returnedMedia.size > 6){
            return returnedMedia.subList(0,6)
        }
        else{
           return returnedMedia
        }
    }

    fun clearChannels(context: Context){
        clearChannel(context)
    }

    fun clearLatestMedias(context: Context){
        clearLatestMedia(context)
    }

//    private fun setFontFaces(context: Context) {
//        val roboto = Typeface.createFromAsset(
//            context.assets,
//            "font/Roboto-Bold.ttf"
//        )
//        channel_header_textView.setTypeface(roboto)
//        browseCategoriesTextview.setTypeface(roboto)
//        newepisode_textview_header.setTypeface(roboto)
//        newepisode_textview_title.setTypeface(roboto)
//        new_episode_textView_channel.setTypeface(roboto)
//        channel_header_textView.setTypeface(roboto)
//        browseCategoriesTextview.setTypeface(roboto)
//    }

}