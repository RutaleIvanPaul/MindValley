package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.ChannelsModel
import com.example.channels.models.DatabaseChannel
import kotlinx.android.synthetic.main.channels_item_layout.view.*
import org.jetbrains.anko.runOnUiThread

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
                holderChannels.textView.text = channelsFromDB[position].title + " SERIES "
            }
            else{
                holderChannels.textView.text = channelsFromDB[position].title
            }
        }
        else {
            if (channelsFromApi[position].series.isNotEmpty()) {
                holderChannels.textView.text = channelsFromApi[position].title + " SERIES "
            } else {
                holderChannels.textView.text = channelsFromApi[position].title
            }
        }
    }

    fun update(){
        context.runOnUiThread {
            notifyDataSetChanged()
        }
    }
}

class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.channels_item
}
