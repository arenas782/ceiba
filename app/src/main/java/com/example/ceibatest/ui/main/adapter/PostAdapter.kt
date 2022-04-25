package com.example.ceibatest.ui.main.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ceibatest.R
import com.example.ceibatest.data.model.responses.Post
import com.example.ceibatest.databinding.ItemPostBinding
import com.example.ceibatest.ui.main.holder.PostViewHolder

class PostAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var posts: MutableList<Post>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                holder.bind(posts[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_post


    override fun getItemCount(): Int =
        if (::posts.isInitialized) posts.size else 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPosts: MutableList<Post>) {
        this.posts = newPosts
        notifyDataSetChanged()
    }
}