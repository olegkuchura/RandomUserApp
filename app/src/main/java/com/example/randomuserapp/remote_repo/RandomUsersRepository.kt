package com.example.randomuserapp.remote_repo

import User

interface RandomUsersRepository {

    suspend fun getUsers(): List<User>

}