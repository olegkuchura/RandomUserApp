package com.example.randomuserapp.view_model

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel(
    user: User
) : ViewModel() {

    private val _userLiveData: MutableLiveData<User> = MutableLiveData(user)
    val userLiveData: LiveData<User>
        get() = _userLiveData

}