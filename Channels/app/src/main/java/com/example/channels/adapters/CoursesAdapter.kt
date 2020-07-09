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

class CoursesAdapter(var latestCourseMediaFromApi: List<ChannelsModel.LatestMedia> = listOf(),
                      var latestCourseMediaFromDB: List<DatabaseMedia> = listOf(),
                      val context: Context):
    RecyclerView.Adapter<CoursesViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        return CoursesViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.courses_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (latestCourseMediaFromApi.isEmpty()){
            latestCourseMediaFromDB.size
        } else {
            latestCourseMediaFromApi.size
        }
    }

    override fun onBindViewHolder(holderChannels: CoursesViewHolder, position: Int) {
        if (latestCourseMediaFromApi.isEmpty()){
                holderChannels.textView.text = latestCourseMediaFromDB[position].title
                Picasso.with(holderChannels.itemView.context).
                load( latestCourseMediaFromDB[position].coverAsset).
                into(holderChannels.imageView)

        }
        else {
                holderChannels.textView.text = latestCourseMediaFromApi[position].title
                Picasso.with(holderChannels.itemView.context).
                load( latestCourseMediaFromApi[position].coverAsset.url).
                into(holderChannels.imageView)

        }
    }
}

class CoursesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.channels_item_textview
    val imageView = view.channel_imageView
}
