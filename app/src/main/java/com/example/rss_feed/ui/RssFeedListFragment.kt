package com.example.rss_feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rss_feed.R
import com.example.rss_feed.adapters.RssFeedAdapter
import com.example.rss_feed.databinding.FragmentRssFeedListBinding
import com.example.rss_feed.models.Feed
import com.example.rss_feed.presentation.RssFeedListPresenter
import com.example.rss_feed.views.RssFeedListView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 20/03/2021.
 */

class RssFeedListFragment : MvpAppCompatFragment(R.layout.fragment_rss_feed_list), RssFeedListView {

    private val presenter: RssFeedListPresenter by moxyPresenter { RssFeedListPresenter() }
    private val binding: FragmentRssFeedListBinding by viewBinding()
    private lateinit var realm: Realm

    private inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rss_feed_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        realm = Realm.getDefaultInstance()
        val feeds = Gson().fromJson<List<Feed>>(requireArguments().getString("feeds")!!)
        setRecyclerView(feeds.sortedByDescending { x -> x.feedPubDate })
    }

    private fun setRecyclerView(rssList: List<Feed>) {
        with(binding) {
            rssRecyclerView.visibility = View.VISIBLE
            rssRecyclerView.layoutManager = LinearLayoutManager(activity)
            rssRecyclerView.adapter = RssFeedAdapter(rssList)
        }
    }

    override fun onSuccessParse(rssList: List<Feed>) {

    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}