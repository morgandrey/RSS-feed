package com.example.rss_feed.models

import io.realm.RealmList
import io.realm.RealmObject
import java.util.*


/**
 * Created by Andrey Morgunov on 20/03/2021.
 */

open class Feed(
    var feedTitle: String = "",
    var feedLink: String = "",
    var feedDescription: String = "",
    var feedPubDate: Date = Date(),
    var feedTags: RealmList<String> = RealmList()
) : RealmObject()
