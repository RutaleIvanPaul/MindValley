package com.example.channels

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("z5AExTtw")
    fun returnNewEpisodes():
            Observable<NewEpisodeModel.NewEpisode>

    @GET("Xt12uVhM")
    fun returnChannels():
            Observable<ChannelsModel.Channels>

    @GET("A0CgArX3")
    fun returnCategories():
            Observable<CategoriesModel.Categories>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://pastebin.com/raw/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}