package com.example.factoryzadatak.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.factoryzadatak.MainActivity
import com.example.factoryzadatak.R
import com.example.factoryzadatak.ui.adapters.ViewPagerAdapter
import com.example.factoryzadatak.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSinglesFragment : Fragment(R.layout.fragment_news_details) {

    private val newsViewModel: NewsViewModel by viewModels()
    private val args: NewsSinglesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        viewPager.setPageTransformer(CompositePageTransformer())

        val adapter = ViewPagerAdapter()
        viewPager.adapter = adapter


        newsViewModel.news.observe(viewLifecycleOwner, Observer { news ->
            if (news.isNullOrEmpty()) {
                (activity as MainActivity).errorPopup()
            } else if (adapter.currentList != news) {
                adapter.submitList(news)
                viewPager.currentItem = args.position
            }
        })


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                (activity as MainActivity).supportActionBar?.title = adapter.currentList[position].title
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        newsViewModel.cancelJobs()
    }


}