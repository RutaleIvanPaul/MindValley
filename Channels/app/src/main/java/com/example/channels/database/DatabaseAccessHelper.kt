package com.example.channels.database

import android.content.Context
import android.util.Log
import com.example.channels.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser

class DatabaseAccessHelper {
    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        ApiService.create()
    }
    fun populateCategory(context: Context){
            disposable = wikiApiServe.returnCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        for (item in result.data.categories) {
                        saveCategory(context, item.name)
                    }
                    },
                    {
                            error ->
                        Log.d("API ERROR","API Fetch Error: ${error.message} ")
                    }
                )

    }

    fun fetchCategories(context: Context):List<DatabaseCategory>{
        return returnCategory(context).parseList(classParser<DatabaseCategory>())
    }

    fun clearCategories(context: Context){
        clearCategory(context)
    }

    fun populateNewEpisodes(context: Context){
        disposable = wikiApiServe.returnNewEpisodes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    for (item in result.data.media) {
                        saveNewEpisode(
                            context, item.channel.title, item.coverAsset.url, item.title, item.type
                        )
                    }
                },
                { error -> Log.d("API ERROR","API Fetch Error: ${error.message} ")}
            )
    }

    fun fetchNewEpisodes(context: Context):List<DatabaseNewEpisode>{
        return returnNewEpisode(context).parseList(classParser<DatabaseNewEpisode>())
    }

    fun clearNewEpisodes(context: Context){
        clearNewEpisode(context)
    }


    fun populateChannels(context: Context){
        disposable = wikiApiServe.returnChannels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    for (item in result.data.channels) {
                        saveChannel(
                            context, item.coverAsset.url?:" ",
                            item.iconAsset.thumbnailUrl?:" ",
                            item.iconAsset.url?:" ", item.id?:" ",
                            item.mediaCount?:0, item.slug?:" ", item.title?:" "
                        )
                    }
                },
                { error -> Log.d("API ERROR","API Fetch Error: ${error.message} ")}
            )
    }

    fun fetchChannels(context: Context):List<DatabaseChannel>{
        return returnChannel(context).parseList(classParser<DatabaseChannel>())
    }

    fun clearChannels(context: Context){
        clearChannel(context)
    }

}