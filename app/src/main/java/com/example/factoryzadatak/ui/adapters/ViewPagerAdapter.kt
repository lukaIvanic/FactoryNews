package com.example.factoryzadatak.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.factoryzadatak.data.model.NewsArticle
import com.example.factoryzadatak.databinding.SingleArticleBinding

class ViewPagerAdapter : ListAdapter<NewsArticle, ViewPagerAdapter.VPViewHolder>(
    DiffCallback()
) {

    inner class VPViewHolder(private val binding: SingleArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsArticle: NewsArticle) {
            binding.apply {
                viewPagerTextview.text = newsArticle.title
                viewPagerDescription.text = newsArticle.description
                Glide.with(itemView)
                    .load(newsArticle.urlToImage)
                    .into(viewPagerImageview)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VPViewHolder {
        val binding =
            SingleArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VPViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VPViewHolder, position: Int) {
        val newsArticle = getItem(position)
        if (newsArticle != null)
            holder.bind(newsArticle)
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem == newItem

    }
}