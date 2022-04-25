package com.example.ceibatest.data.api

import com.example.ceibatest.data.model.responses.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsAPI {
    @GET("posts")
    suspend fun getPostByUser(@Query("userId") userId : Int): List<Post>?
}