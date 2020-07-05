package com.example.channels.database

data class DatabaseCategory(val id: Long,val name: String)

data class DatabaseNewEpisode(
    val id: Long,val channel: String, val coverAsset: String, val title: String, val type: String
)

data class DatabaseChannel(
    val id: Long, val coverAsset_url: String,val iconAsset_thumbnail_url:String,
    val iconAsset_url:String,val channel_id:String,val mediaCount:Long, val slug:String,
    val title: String
)
