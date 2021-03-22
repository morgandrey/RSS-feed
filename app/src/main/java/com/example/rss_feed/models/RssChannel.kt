package com.example.rss_feed.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 * Created by Andrey Morgunov on 22/03/2021.
 */

open class RssChannel(
    @PrimaryKey var id: String = "",
    var rssChannelName: String = "",
    var rssChannelLink: String = "",
    var rssChannelFeedList: RealmList<Feed> = RealmList()
) : RealmObject()