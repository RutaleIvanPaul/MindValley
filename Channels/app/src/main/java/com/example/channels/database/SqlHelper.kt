package com.example.channels.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "channels") {

    companion object {
        private var instance: SqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SqlHelper {
            if (instance == null) {
                instance = SqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.createTable("category",true,
            "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "category_name" to TEXT)

        db.createTable("new_episode",true,
            "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "channel" to TEXT,
            "coverAsset" to TEXT,
            "title" to TEXT,
            "type" to TEXT)

        db.createTable("channel",true,
            "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "coverAsset_url" to TEXT,
            "iconAsset_thumbnail_url" to TEXT,
            "iconAsset_url" to TEXT,
            "channel_id" to TEXT,
            "mediaCount" to INTEGER,
            "slug" to TEXT,
            "title" to TEXT
        )

        db.createTable("latest_media",true,
            "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "channel_foreign" to TEXT,
            "coverAsset" to TEXT,
            "title" to TEXT,
            "type" to TEXT)

        db.createTable("series",true,
            "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "channel_foreign" to TEXT,
            "coverAsset" to TEXT,
            "series_id" to TEXT,
            "title" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}


// Access property for Context
val Context.database: SqlHelper
    get() = SqlHelper.getInstance(applicationContext)

fun saveCategory(context: Context,category_name:String):Long{
    val row_id = context.database.use {
        insert("category","category_name" to category_name )
    }
    return row_id
}

fun saveNewEpisode(context: Context,channel:String,coverAsset:String,title: String,type:String):Long{
    val row_id = context.database.use {
        insert("new_episode",
            "channel" to channel,
                    "coverAsset" to coverAsset,
                    "title" to title,
                    "type" to type )
    }
    return row_id
}

fun saveChannel(context: Context,coverAsset_url: String,iconAsset_thumbnail_url:String,
                iconAsset_url:String,channel_id:String,mediaCount:Int, slug:String,
                title: String ):Long{

    val row_id = context.database.use {
        insert("channel",
            "coverAsset_url" to coverAsset_url,
            "iconAsset_thumbnail_url" to iconAsset_thumbnail_url,
            "iconAsset_url" to iconAsset_url,
            "channel_id" to channel_id,
            "mediaCount" to mediaCount,
            "slug" to slug,
            "title" to title)
    }
    return row_id
}

fun saveLatestMedia(context: Context,channel_foreign:String,coverAsset: String,series_id:String,
                    title: String): Long{
    val row_id = context.database.use {
        insert("latest_media",
            "channel_foreign" to channel_foreign,
            "coverAsset" to coverAsset,
            "series_id" to series_id,
            "title" to title)
    }
    return row_id
}

fun saveSeries(context: Context,channel_foreign: String,coverAsset: String,series_id: String,
               title: String):Long{
    val row_id = context.database.use {
        insert("category",
            "channel_foreign" to channel_foreign,
            "coverAsset" to coverAsset,
            "series_id" to series_id,
            "title" to title )
    }
    return row_id
}

fun returnCategory(context: Context) =
    context.database.readableDatabase.select("category")

fun returnNewEpisode(context: Context) =
    context.database.readableDatabase.select("new_episode")

fun returnChannel(context: Context) =
    context.database.readableDatabase.select("channel")



fun clearCategory(context: Context) =
    context.database.use {
        delete("category")
    }

fun clearNewEpisode(context: Context) =
    context.database.use {
        delete("new_episode")
    }

fun clearChannel(context: Context) =
    context.database.use {
        delete("channel")
    }

