package com.example.rss_feed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rss_feed.R
import com.example.rss_feed.models.RssChannel
import com.google.gson.Gson
import io.realm.Realm


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

class RssChannelAdapter(private val dataSet: List<RssChannel>) :
    RecyclerView.Adapter<RssChannelAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val channelName = itemView.findViewById<TextView>(R.id.rss_channel_name_text_view)
        private val channelFeedCount = itemView.findViewById<TextView>(R.id.rss_channel_feed_count_text_view)

        fun bind(channel: RssChannel) {
            channelName.text = channel.rssChannelName
            channelFeedCount.text = channel.rssChannelFeedList.count().toString()
            val realm = Realm.getDefaultInstance()
            itemView.setOnClickListener {
                val bundle = bundleOf("feeds" to Gson().toJson(realm.copyFromRealm(channel.rssChannelFeedList)))
                it.findNavController().navigate(R.id.action_rssChannelListFragment_to_rssFeedListFragment, bundle)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.rss_channel_item_view, parent, false)
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