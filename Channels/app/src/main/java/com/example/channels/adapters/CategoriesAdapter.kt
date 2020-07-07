package com.example.channels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.channels.R
import com.example.channels.models.CategoriesModel
import com.example.channels.models.DatabaseCategory
import kotlinx.android.synthetic.main.categories_item_layout.view.*

class CategoriesAdapter (
    val categoriesFromApi: List<CategoriesModel.Category> = listOf(),
    val categoriesFromDB: List<DatabaseCategory> = listOf(),
    val context: Context):
    RecyclerView.Adapter<CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.categories_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (categoriesFromApi.isEmpty()){
            categoriesFromDB.size
        } else {
            categoriesFromApi.size
        }
    }

    override fun onBindViewHolder(holderCategories: CategoriesViewHolder, position: Int) {
        if (categoriesFromApi.isEmpty()){
            holderCategories.textView.text = categoriesFromDB[position].name
        }
        else {
            holderCategories.textView.text = categoriesFromApi[position].name
        }
    }
}

class CategoriesViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.list_item
}

