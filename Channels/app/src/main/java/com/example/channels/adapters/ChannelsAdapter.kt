package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.ChannelsModel
import com.example.channels.models.DatabaseChannel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.channels_item_layout.view.*

class ChannelsAdapter(var channelsFromApi: List<ChannelsModel.Channel> = listOf(),
                      var channelsFromDB: List<DatabaseChannel> = listOf(),
                      val context: Context):
    RecyclerView.Adapter<ChannelsViewHolder>()  {

//    var channels: List<ChannelsModel.Channel> = listOf(ChannelsModel.Channel(""))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        return ChannelsViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.channels_item_layout,
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
        if (channelsFromApi.isEmpty()){
            if (channelsFromDB[position].seriesOrNot.equals("true")){
//                holderChannels.layout.minWidth
                holderChannels.textView.text = channelsFromDB[position].title + " SERIES "
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromDB[position].coverAsset_url).
                into(holderChannels.imageView)
            }
            else{
                holderChannels.textView.text = channelsFromDB[position].title
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromDB[position].coverAsset_url).
                into(holderChannels.imageView)
            }
        }
        else {
            if (channelsFromApi[position].series.isNotEmpty()) {
                holderChannels.textView.text = channelsFromApi[position].title + " SERIES "
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromApi[position].coverAsset.url).
                into(holderChannels.imageView)
            } else {
                holderChannels.textView.text = channelsFromApi[position].title
                Picasso.with(holderChannels.itemView.context).
                load( channelsFromApi[position].coverAsset.url).
                into(holderChannels.imageView)
            }
        }
    }
}

class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.channels_item_textview
    val imageView = view.channel_imageView
    val layout = view.channels_constraint_layout
}
