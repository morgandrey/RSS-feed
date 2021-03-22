package com.example.rss_feed.views

import com.example.rss_feed.models.RssChannel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

@AddToEndSingle
interface RssChannelListView : MvpView {
    fun onSuccessParse(channel: RssChannel)
    fun switchProgress(show: Boolean)
    fun showError(message: String)
}