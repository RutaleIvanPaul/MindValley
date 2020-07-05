package com.example.channels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.channels.database.DatabaseAccessHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val wikiApiServe by lazy {
        ApiService.create()
    }

    val databaseAccessHelper = DatabaseAccessHelper()

    private fun beginSearch() {
        disposable = wikiApiServe.returnCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> txt_search_result.text = "${result.data.categories.toString()} result found" },
                { error -> txt_search_result.text = "${error.message}"}
            )
    }

    private fun returnCategoriesToScreen(){
        txt_search_result.text = databaseAccessHelper.fetchChannels(applicationContext).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            returnCategoriesToScreen()
        }
        databaseAccessHelper.clearChannels(applicationContext)
        databaseAccessHelper.populateChannels(applicationContext)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}