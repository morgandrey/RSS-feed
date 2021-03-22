package com.example.rss_feed.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rss_feed.R
import com.example.rss_feed.databinding.FragmentRssFeedListBinding
import com.example.rss_feed.presentation.RssFeedListPresenter
import com.example.rss_feed.views.RssFeedListView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 20/03/2021.
 */

class RssFeedListFragment : MvpAppCompatFragment(R.layout.fragment_rss_feed_list), RssFeedListView {

    private val presenter: RssFeedListPresenter by moxyPresenter { RssFeedListPresenter() }
    private val binding: FragmentRssFeedListBinding by viewBinding()

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

                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            deleteRssButton.setOnClickListener {

            }
        }
    }

    override fun onSuccessParse() {
        TODO("Not yet implemented")
    }

    override fun switchProgress(show: Boolean) {
        TODO("Not yet implemented")
    }

}