package com.example.rss_feed.models


/**
 * Created by Andrey Morgunov on 20/03/2021.
 */

data class Feed(
    val feedTitle: String,
    val feedLink: String,
    val feedDescription: String,
    val feedPubDate: String,
    val feedTags: List<String>
)
