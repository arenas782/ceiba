package com.example.ceibatest.data.api

import com.example.ceibatest.data.model.responses.User
import retrofit2.http.GET

interface UsersAPI {
    @GET("users")
    suspend fun getUsers(): List<User>?
}