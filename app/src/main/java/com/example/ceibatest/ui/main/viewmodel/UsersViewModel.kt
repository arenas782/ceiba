package com.example.ceibatest.ui.main.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.ceibatest.data.model.responses.User
import com.example.ceibatest.data.repository.UsersRepository
import com.example.ceibatest.ui.main.adapter.UserAdapter
import com.example.ceibatest.utils.Commons.getTextToSave
import com.example.ceibatest.utils.EventLiveData
import com.example.ceibatest.utils.Resource
import com.example.ceibatest.utils.UtilityTextWatcherOnTextChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel()  {

    private var _users = MutableLiveData<Resource<List<User>?>>()
    private val usersList : MutableList <User> = arrayListOf()

    val usersAdapter = UserAdapter {
        _userDetails.value = it
        _actionDetailsUser.value = EventLiveData(true)
    }

    var searchTW = UtilityTextWatcherOnTextChanged {
        usersAdapter.filter(it.getTextToSave())

    }

    val users : LiveData<Resource<List<User>?>>
        get () = _users

    private var _actionDetailsUser = MutableLiveData<EventLiveData<Boolean>>()
    var actionDetailsUser: LiveData<EventLiveData<Boolean>> = _actionDetailsUser

    private var _userDetails = MutableLiveData<User>()
    var userDetails : LiveData<User> = _userDetails


    private fun fetchUsers(){
        viewModelScope.launch {
            repository.getUsers().onEach {
                Log.e("TAG",it.data.toString())
                _users.value = it
                it.data?.let { usersList -> updateUsers(usersList) }
            }.launchIn(viewModelScope)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUsers(list: List<User>?){
        usersList.clear()
        if (list != null) {
            for (item in list)
                usersList.add(item)
        }
        usersAdapter.updateData(usersList)
    }

    init {
        fetchUsers()
    }
}