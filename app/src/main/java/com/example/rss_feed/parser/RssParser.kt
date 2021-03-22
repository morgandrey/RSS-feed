package com.example.rss_feed.parser

import com.example.rss_feed.models.Feed
import com.example.rss_feed.models.RssChannel
import com.example.rss_feed.utils.Utils.convertToDateViaInstant
import io.reactivex.Observable
import io.realm.RealmList
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

object RssParser {
    fun parseRssChannel(url: String): Observable<RssChannel> {
        return Observable.create {
            val doc = Jsoup.connect(url).get()
            val channelName = doc.select("channel").select("title").first().text()
            val items = doc.select("item")
            val feedList = RealmList<Feed>()
            for (item in items) {
                val feedTitle = item.select("title").text()
                val feedLink = item.select("link").text()
                val feedDescription = item.select("description").text()
                val feedPubDate = item.select("pubDate").text()
                val feedCategories = item.select("category")
                val feedCategoryList = RealmList<String>()
                for (category in feedCategories) {
                    feedCategoryList.add(category.text())
                }
                feedList.add(
                    Feed(
                        feedTitle = feedTitle,
                        feedLink = feedLink,
                        feedDescription = feedDescription,
                        feedPubDate = convertToDateViaInstant(LocalDateTime.parse(feedPubDate, DateTimeFormatter.RFC_1123_DATE_TIME)),
                        feedTags = feedCategoryList
                    )
                )
            }
            val rssChannel = RssChannel(
                rssChannelName = channelName,
                rssChannelLink = url,
                rssChannelFeedList = feedList
            )
            it.onNext(rssChannel)
        }
    }
}