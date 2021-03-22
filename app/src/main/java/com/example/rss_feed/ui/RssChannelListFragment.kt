package com.example.rss_feed.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rss_feed.R
import com.example.rss_feed.adapters.RssChannelAdapter
import com.example.rss_feed.databinding.FragmentRssChannelListBinding
import com.example.rss_feed.models.RssChannel
import com.example.rss_feed.presentation.RssChannelListPresenter
import com.example.rss_feed.views.RssChannelListView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.*


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

class RssChannelListFragment : MvpAppCompatFragment(R.layout.fragment_rss_channel_list),
    RssChannelListView {

    private val presenter: RssChannelListPresenter by moxyPresenter { RssChannelListPresenter() }
    private val binding: FragmentRssChannelListBinding by viewBinding()
    private lateinit var realm: Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rss_channel_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realm = Realm.getDefaultInstance()
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        with(binding) {
            addRssButton.setOnClickListener {
                val editText = EditText(requireContext())
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                editText.layoutParams = layoutParams
                AlertDialog.Builder(requireContext())
                    .setTitle("RSS link")
                    .setMessage("Please input rss link")
                    .setView(editText)
                    .setPositiveButton("OK") { _, _ ->
                        presenter.getRssChannel(editText.text.toString())
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            swipeRefreshView.setOnRefreshListener {
                swipeRefreshView.isRefreshing = false
                getAllChannels().forEach { x -> presenter.getRssChannel(x.rssChannelLink) }
            }
            deleteRssButton.setOnClickListener {

            }
        }

        val channels = getAllChannels()
        if (channels.isNullOrEmpty()) {
            binding.rssChannelRecyclerView.visibility = View.GONE
        } else {
            setRecyclerView(channels)
        }
    }

    override fun onSuccessParse(channel: RssChannel) {
        if (channel.rssChannelFeedList.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Ничего не найдено", Toast.LENGTH_SHORT).show()
        } else {
            val savedRssChannel =
                realm.where<RssChannel>().equalTo("rssChannelName", channel.rssChannelName)
                    .findFirst()
            if (savedRssChannel != null) {
                realm.beginTransaction()
                channel.id = savedRssChannel.id
                realm.copyToRealmOrUpdate(channel)
                realm.commitTransaction()
            } else {
                realm.beginTransaction()
                channel.id = UUID.randomUUID().toString()
                realm.copyToRealmOrUpdate(channel)
                realm.commitTransaction()
            }
            setRecyclerView(getAllChannels())
        }
    }

    private fun setRecyclerView(rssChannelList: List<RssChannel>) {
        with(binding) {
            rssChannelRecyclerView.visibility = View.VISIBLE
            rssChannelRecyclerView.layoutManager = LinearLayoutManager(activity)
            rssChannelRecyclerView.adapter = RssChannelAdapter(rssChannelList)
        }
    }

    private fun getAllChannels(): RealmResults<RssChannel> {
        return realm.where<RssChannel>().findAll()
    }

    override fun switchProgress(show: Boolean) {
        when (show) {
            true -> {
                with(binding) {
                    syncTextView.visibility = View.VISIBLE
                    addRssButton.visibility = View.INVISIBLE
                    deleteRssButton.visibility = View.INVISIBLE
                }
            }
            false -> {
                with(binding) {
                    syncTextView.visibility = View.INVISIBLE
                    addRssButton.visibility = View.VISIBLE
                    deleteRssButton.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}