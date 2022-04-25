package com.example.ceibatest.ui.main.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ceibatest.data.model.responses.Post
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.data.repository.PostsRepository
import com.example.ceibatest.data.repository.UsersRepository
import com.example.ceibatest.ui.main.adapter.PostAdapter
import com.example.ceibatest.ui.main.adapter.UserAdapter
import com.example.ceibatest.utils.EventLiveData
import com.example.ceibatest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostsRepository) : ViewModel()  {

    private val postsList : MutableList <Post> = arrayListOf()

    val postsAdapter = PostAdapter()

    fun getPostsByUser(userId : Int) = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            val data = repository.getPostsByUser(userId)
            updatePosts(data)
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.stackTraceToString() ?: "Error Occurred!"))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updatePosts(list: List<Post>?){
        postsList.clear()
        if (list != null) {
            for (item in list)
                postsList.add(item)
        }
        postsAdapter.updateData(postsList)
    }
}