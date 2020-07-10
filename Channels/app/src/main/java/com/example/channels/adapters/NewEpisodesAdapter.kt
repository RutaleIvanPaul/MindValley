package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.DatabaseNewEpisode
import com.example.channels.models.NewEpisodeModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.newepisodes_layout.view.*

class NewEpisodesAdapter(
    val newEpisodesFromApi: List<NewEpisodeModel.Media> = listOf() ,
    val newEpisodesFromDB: List<DatabaseNewEpisode> = listOf(),
    val context: Context):
    RecyclerView.Adapter<NewEpisodesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewEpisodesViewHolder {
        return NewEpisodesViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.newepisodes_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (newEpisodesFromApi.isEmpty()){
            newEpisodesFromDB.size
        } else {
            newEpisodesFromApi.size
        }
    }

    override fun onBindViewHolder(holderNewEpisodes: NewEpisodesViewHolder, position: Int) {
        if (newEpisodesFromApi.isEmpty()){
            holderNewEpisodes.textView_title.text = newEpisodesFromDB[position].title
            holderNewEpisodes.textView_channel.text = newEpisodesFromDB[position].channel
            Picasso.with(holderNewEpisodes.itemView.context).
            load( newEpisodesFromDB[position].coverAsset).
            into(holderNewEpisodes.imageView)
        }
        else {
            holderNewEpisodes.textView_title.text = newEpisodesFromApi[position].title
            holderNewEpisodes.textView_channel.text = newEpisodesFromApi[position].channel.title
            Picasso.with(holderNewEpisodes.itemView.context).
            load( newEpisodesFromApi[position].coverAsset.url).
            into(holderNewEpisodes.imageView)
        }
    }

}

class NewEpisodesViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val textView_title = view.newepisode_textview_title
    val textView_channel = view.new_episode_textView_channel
    val imageView = view.newepisode_imageView
}

