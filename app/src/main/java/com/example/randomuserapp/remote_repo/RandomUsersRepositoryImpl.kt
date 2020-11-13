package com.example.randomuserapp.remote_repo

import User

class RandomUsersRepositoryImpl(
    private val api: RandomUserService
): RandomUsersRepository {

    companion object {
        private const val RESULTS = 1000
    }

    override suspend fun getUsers(): List<User> {
        val response = api.getUsers(RESULTS)
        return response.results
    }
}