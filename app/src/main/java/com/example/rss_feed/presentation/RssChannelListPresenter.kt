package com.example.rss_feed.presentation

import com.example.rss_feed.parser.RssParser
import com.example.rss_feed.views.RssChannelListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import java.lang.IllegalArgumentException


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

class RssChannelListPresenter : MvpPresenter<RssChannelListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getRssChannel(url: String) {
        viewState.switchProgress(true)
        compositeDisposable.add(
            RssParser.parseRssChannel(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { channel ->
                    viewState.switchProgress(false)
                    viewState.onSuccessParse(channel)
                },
                { error ->
                    viewState.switchProgress(false)
                    if (error is IllegalArgumentException) {
                        viewState.showError("Неправильно введеный URL")
                    } else {
                        viewState.showError(error.toString())
                    }
                }
            )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}