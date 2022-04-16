package com.bhaskar.test3.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.test3.R
import com.bhaskar.test3.databinding.ItemMainRecyclerviewBinding
import com.bhaskar.test3.models.Article
import com.bhaskar.test3.models.News
import com.bumptech.glide.Glide

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>(){
    private lateinit var viewBinding: ItemMainRecyclerviewBinding
    private val articles: List<Article> = listOf()
    private var news = News(articles, "", 0)

    fun submitData(news: News) {
        this.news = news
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclerAdapter.MyViewHolder {
        viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_recyclerview, parent, false)
        return MyViewHolder(viewBinding.root)
    }

    override fun onBindViewHolder(holder: MainRecyclerAdapter.MyViewHolder, position: Int) {
        val singleNews = news.articles[position]
        with(viewBinding) {
            newsTitle.text = singleNews.title
            setNewsImage(position)
            newsAuthor.text = singleNews.author
            newsSource.text = singleNews.source.name
            newsDecription.text = singleNews.description
            newsContent.text = singleNews.content
        }
    }

    private fun setNewsImage(position: Int) {
        with(viewBinding) {
            Glide.with(newsImage)
                .load(news.articles[position].urlToImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fallback(R.drawable.error)
                .into(newsImage)
        }
    }

    override fun getItemCount() = news.articles.size
}