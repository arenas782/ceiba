package com.example.ceibatest.data.repository

import android.util.Log
import com.example.ceibatest.App
import com.example.ceibatest.data.api.UsersAPI
import com.example.ceibatest.data.database.UserDao
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.utils.Commons
import com.example.ceibatest.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UsersRepository @Inject constructor(private val userDao: UserDao, private val usersAPI: UsersAPI) {
    suspend fun getUsers(): Flow<Resource<List<User>>> = flow{
        emit(Resource.loading(data = null))
        try {
            if (Commons.isOnline(App.appContext)){
                val remoteUsers = usersAPI.getUsers()
                remoteUsers?.forEach{
                    Log.e("POST",it.toString())
                    userDao.insertUser(it)
                }
            }
            val posts = userDao.getAllUsers()
            emit(Resource.success(data = posts))
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e    .message ?: "Error Occurred!"))
        }
    }
}