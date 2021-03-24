package com.example.rss_feed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rss_feed.R
import com.example.rss_feed.models.Feed


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

class RssFeedAdapter(private val dataSet: List<Feed>) :
    RecyclerView.Adapter<RssFeedAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val feedTitle = itemView.findViewById<TextView>(R.id.feed_title_text_view)
        private val feedDate = itemView.findViewById<TextView>(R.id.feed_date_text_view)
        private val feedDescription = itemView.findViewById<TextView>(R.id.feed_description_text_view)
        private val feedTags = itemView.findViewById<TextView>(R.id.feed_tags_text_view)

        fun bind(feed: Feed) {
            feedTitle.text = feed.feedTitle
            feedDate.text = feed.feedPubDate.toString()
            feedDescription.text = feed.feedDescription

            itemView.setOnClickListener {
                val bundle = bundleOf("feedLink" to feed.feedLink)
                it.findNavController().navigate(R.id.action_rssFeedListFragment_to_rssFeedWebFragment, bundle)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.rss_feed_item_view, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataSet.size
}