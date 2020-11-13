package com.example.randomuserapp.view_model

import User
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomuserapp.Event
import com.example.randomuserapp.enums.DataState
import com.example.randomuserapp.remote_repo.RandomUsersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserListViewModel(
    private val repository: RandomUsersRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _stateLiveData: MutableLiveData<DataState> = MutableLiveData(DataState.LOADING)
    val stateLiveData: LiveData<DataState>
        get() = _stateLiveData

    private val _usersLiveData: MutableLiveData<List<User>> = MutableLiveData(emptyList())
    val usersLiveData: LiveData<List<User>>
        get() = _usersLiveData

    private val _navigationToUserLiveData = MutableLiveData<Event<User>>()
    val navigationToUserLiveData: LiveData<Event<User>>
        get() = _navigationToUserLiveData

    init {
        loadUsers()
    }

    fun onUserClicked(user: User) {
        _navigationToUserLiveData.value = Event(user)
    }

    fun onRefresh() {
        _stateLiveData.value = DataState.LOADING
        loadUsers()
    }

    private fun loadUsers() = uiScope.launch {
        try {
            val loadedUsers = repository.getUsers()
            _usersLiveData.value = loadedUsers
            _stateLiveData.value = DataState.SUCCESS
        } catch (ex: Exception) {
            Log.d("AndroidRuntime", ex.toString())
            _stateLiveData.value = DataState.ERROR
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}