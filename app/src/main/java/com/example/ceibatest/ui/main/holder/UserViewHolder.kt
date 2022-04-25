package com.example.ceibatest.ui.main.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.databinding.ItemUserBinding

class UserViewHolder( val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: User) {
        binding.user = data
        binding.executePendingBindings()
    }
}