package com.example.factoryzadatak.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.factoryzadatak.MainActivity
import com.example.factoryzadatak.viewmodel.NewsViewModel
import com.example.factoryzadatak.R
import com.example.factoryzadatak.ui.adapters.NewsRowsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsBrowseFragment : Fragment(R.layout.fragment_news_browse), NewsRowsAdapter.ArticleListener {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsProgressBar = view.findViewById<ProgressBar>(R.id.news_progress_bar)

        val rec_view = view.findViewById<RecyclerView>(R.id.rec_view)
        val adapter = NewsRowsAdapter(this)
        rec_view.adapter = adapter
        rec_view.setHasFixedSize(true)



        newsViewModel.news.observe(viewLifecycleOwner, Observer { news ->
            if (news.isNullOrEmpty()) {
                (activity as MainActivity).errorPopup()
                newsProgressBar.isVisible = false

            } else if (adapter.currentList != news) {
                adapter.submitList(news)
                newsProgressBar.isVisible = false
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        newsViewModel.cancelJobs()
    }

    override fun onArticleClick(position: Int, title: String) {
        val gotoDetailsAction =
            NewsBrowseFragmentDirections.actionNewsBrowseFragmentToDetailsFragment(position, title)
        findNavController(this).navigate(gotoDetailsAction)
    }

}