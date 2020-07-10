package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.ChannelsModel
import com.example.channels.models.DatabaseChannel
import com.example.channels.services.RequestsManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.channel_title_recyclerview.view.*

class ChannelsAdapter(var channelsFromApi: List<ChannelsModel.Channel> = listOf(),
                      var channelsFromDB: List<DatabaseChannel> = listOf(),
                      val context: Context
):
    RecyclerView.Adapter<ChannelsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.channel_title_recyclerview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (channelsFromApi.isEmpty()){
            channelsFromDB.size
        } else {
            channelsFromApi.size
        }
    }

    override fun onBindViewHolder(holderChannels: ChannelsViewHolder, position: Int) {
        holderChannels.recycler_in_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        if (channelsFromApi.isEmpty()){
            holderChannels.textView_channel_header.text = channelsFromDB[position].title
            holderChannels.textView_channel_header.text = channelsFromDB[position].mediaCount.toString() + " episodes"
            Picasso.with(holderChannels.itemView.context).
                load( channelsFromDB[position].coverAsset_url).
                into(holderChannels.imageView)
            if (channelsFromDB[position].seriesOrNot.equals("true")){
                holderChannels.recycler_in_recycler.adapter = SeriesAdapter(
                    latestSeriesMediaFromDB =RequestsManager().fetchMedia(context,channelsFromDB[position].title),
                    context = context
                )
            }
            else{
                holderChannels.recycler_in_recycler.adapter = CoursesAdapter(
                    latestCourseMediaFromDB = RequestsManager().fetchMedia(context,channelsFromDB[position].title),
                    context = context
                )
            }

        }
        else {
            holderChannels.textView_channel_header.text = channelsFromApi[position].title
            holderChannels.textView_channel_subheader.text =
                    channelsFromApi[position].mediaCount.toString() + " episodes"
            if (channelsFromApi[position].iconAsset != null){
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromApi[position].iconAsset.thumbnailUrl).
                into(holderChannels.imageView)
            }
            else{
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromApi[position].coverAsset.url).
                into(holderChannels.imageView)
            }

            if (channelsFromApi[position].series.isEmpty()){
                holderChannels.recycler_in_recycler.adapter = CoursesAdapter(
                    latestCourseMediaFromApi = channelsFromApi[position].latestMedia.subList(0,6),
                    context = context
                )
            }
            else{
                val latestMedia = channelsFromApi[position].latestMedia
                if (latestMedia.size > 6){
                    holderChannels.recycler_in_recycler.adapter = SeriesAdapter(
                        latestSeriesMediaFromApi = channelsFromApi[position].latestMedia.subList(0,6),
                        context = context
                    )
                }
                else{
                    holderChannels.recycler_in_recycler.adapter = SeriesAdapter(
                        latestSeriesMediaFromApi = channelsFromApi[position].latestMedia,
                        context = context
                    )
                }
            }

        }
    }
}

class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView_channel_header = view.channel_header_textView
    val textView_channel_subheader = view.channel_subheader_textView
    val imageView = view.channel_header_imageView
    val recycler_in_recycler = view.latestMedia_recyclerview
}
