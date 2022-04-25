package com.example.ceibatest.ui.main.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.ceibatest.R
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.databinding.ItemUserBinding
import com.example.ceibatest.ui.main.holder.UserViewHolder
import java.util.*

class UserAdapter(private val setupClickCallback: (user : User) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var users: MutableList<User>
    lateinit var filterUsers: MutableList<User>

    private val _listSize = MutableLiveData(-1)
    val listSize : LiveData<Int> = _listSize


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> {
                holder.bind(filterUsers[position])
                holder.binding.btnSeePosts.setOnClickListener { setupClickCallback(filterUsers[position]) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_user

    override fun getItemCount(): Int =
        if (::filterUsers.isInitialized) filterUsers.size else 0

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newUsers: MutableList<User>) {
        this.users = newUsers
        filterUsers = newUsers
        _listSize.value = filterUsers.size
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        val filter: MutableList<User> = arrayListOf()

        for (user in users) {
            if ("${user.name?.lowercase(Locale.getDefault())}"
                    .contains(query.lowercase(Locale.getDefault()))
            ) {
                filter.add(user)
            }
        }
        this.filterUsers = filter
        _listSize.value = filterUsers.size
        notifyDataSetChanged()
    }
}