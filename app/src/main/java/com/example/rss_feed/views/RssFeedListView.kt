package com.example.rss_feed.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 20/03/2021.
 */

@AddToEndSingle
interface RssFeedListView : MvpView {
    fun onSuccessParse()
    fun switchProgress(show: Boolean)
}