package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.ChannelsModel
import com.example.channels.models.DatabaseChannel
import com.example.channels.models.DatabaseMedia
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.courses_item_layout.view.*
import kotlinx.android.synthetic.main.series_item_layout.view.*

class SeriesAdapter(var latestSeriesMediaFromApi: List<ChannelsModel.LatestMedia> = listOf(),
                     var latestSeriesMediaFromDB: List<DatabaseMedia> = listOf(),
                     val context: Context):
    RecyclerView.Adapter<SeriesViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.series_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (latestSeriesMediaFromApi.isEmpty()){
            latestSeriesMediaFromDB.size
        } else {
            latestSeriesMediaFromApi.size
        }
    }

    override fun onBindViewHolder(holderChannels: SeriesViewHolder, position: Int) {
        if (latestSeriesMediaFromApi.isEmpty()){
            holderChannels.textView.text = latestSeriesMediaFromDB[position].title
            Picasso.with(holderChannels.itemView.context).
            load( latestSeriesMediaFromDB[position].coverAsset).
            into(holderChannels.imageView)

        }
        else {
            holderChannels.textView.text = latestSeriesMediaFromApi[position].title
            Picasso.with(holderChannels.itemView.context).
            load( latestSeriesMediaFromApi[position].coverAsset.url).
            into(holderChannels.imageView)

        }
    }
}

class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.seriesitem_textview
    val imageView = view.series_imageview
}
