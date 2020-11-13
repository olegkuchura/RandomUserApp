package com.example.randomuserapp.remote_repo

import UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("/api/?inc=gender,name,location,email,dob,phone,cell,picture")
    suspend fun getUsers(@Query("results") results: Int): UsersResponse

}