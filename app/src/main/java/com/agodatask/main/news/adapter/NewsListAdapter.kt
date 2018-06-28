package com.agodatask.main.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agodatask.R
import com.agodatask.main.news.adapter.news.NewsViewHolder
import com.agodatask.main.news.adapter.news.NewsViewModel

/**
 * Created by Sid on 27/06/2018.
 */

class NewsListAdapter(private val list: List<ListItem>, private val listener: OnListInteraction) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NEWS = 0
        const val UNKNOWN = 1
    }

    interface OnListInteraction {
        fun onNewsClicked(newsTitle: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_news, parent, false)
                NewsViewHolder(view, object : NewsViewHolder.OnNewsViewHolderInteraction {
                    override fun onNewsClicked(newsTitle: String) {
                        listener.onNewsClicked(newsTitle)
                    }
                })
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.empty_silly_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NewsViewHolder) {
            val model = list[position] as NewsViewModel
            holder.init(model)
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when {
            list[position].itemType == ListItem.ViewType.NEWS -> NEWS
            else -> UNKNOWN
        }
    }


    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}