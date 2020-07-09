package com.example.channels.models

object CategoriesModel{
    data class Categories(val data: Data)
    data class Data(val categories: List<Category>)
    data class Category(val name: String)
}

object NewEpisodeModel{
    data class NewEpisode(val `data`: Data)
    data class Data(val media: List<Media>)
    data class Media(
        val channel: Channel,
        val coverAsset: CoverAsset,
        val title: String,
        val type: String
    )
    data class Channel(val title: String)
    data class CoverAsset(val url: String)
}

object ChannelsModel{
    data class Channels(val data: Data)
    data class Data(val channels: List<Channel>)
    data class Channel(
        val coverAsset: CoverAsset,
        val iconAsset: IconAsset,
        val id: String?,
        val latestMedia: List<LatestMedia>,
        val mediaCount: Int?,
        val series: List<Sery>,
        val slug: String?,
        val title: String?
    )
    data class CoverAsset(val url: String?)
    data class IconAsset(
        val thumbnailUrl: String?,
        val url: String?
    )
    data class LatestMedia(
        val coverAsset: CoverAssetX,
        val title: String,
        val type: String
    )
    data class Sery(
        val coverAsset: CoverAssetXX,
        val id: String,
        val title: String
    )
    data class CoverAssetX(
        val url: String?
    )
    data class CoverAssetXX(
        val url: String
    )
}