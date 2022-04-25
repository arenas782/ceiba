package com.example.ceibatest.ui.main.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.ceibatest.data.model.responses.Post
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.databinding.ItemPostBinding
import com.example.ceibatest.databinding.ItemUserBinding

class PostViewHolder(private val binding : ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Post) {
        binding.post = data
        binding.executePendingBindings()
    }
}