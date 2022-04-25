package com.example.ceibatest.data.repository

import com.example.ceibatest.data.api.PostsAPI
import javax.inject.Inject

class PostsRepository @Inject constructor(private val postsAPI: PostsAPI) {
    suspend fun getPostsByUser(userId : Int) = postsAPI.getPostByUser(userId)
}