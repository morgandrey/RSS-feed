package com.example.rss_feed.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rss_feed.R
import com.example.rss_feed.databinding.FragmentRssFeedWebBinding
import moxy.MvpAppCompatFragment


/**
 * Created by Andrey Morgunov on 23/03/2021.
 */

class RssFeedWebFragment : MvpAppCompatFragment(R.layout.fragment_rss_feed_web) {

    private val binding: FragmentRssFeedWebBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rss_feed_web, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedLink = requireArguments().getString("feedLink")
        val navController = findNavController()
        with(binding) {
            toolbar.setupWithNavController(navController)
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    webView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    if (newProgress == 100) {
                        webView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
            webView.loadUrl(feedLink!!)
        }
    }
}