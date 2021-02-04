package com.example.factoryzadatak.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.factoryzadatak.data.model.NewsArticle
import com.example.factoryzadatak.databinding.RowArticleBinding

class NewsRowsAdapter(val onArticleListener: ArticleListener) :
    ListAdapter<NewsArticle, NewsRowsAdapter.NewsViewHolder>(DiffCallback()) {

    interface ArticleListener {
        fun onArticleClick(position: Int, title: String)
    }


    inner class NewsViewHolder(private val binding: RowArticleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(newsArticle: NewsArticle) {
            binding.apply {
                titleTextView.text = newsArticle.title
                Glide.with(binding.root)
                    .load(newsArticle.urlToImage)
                    .centerCrop()
                    .into(imageView)
                root.setOnClickListener(this@NewsViewHolder)
            }
        }


        override fun onClick(v: View?) =
            onArticleListener.onArticleClick(adapterPosition, getItem(adapterPosition).title)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = RowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }


    class DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem == newItem

    }


}