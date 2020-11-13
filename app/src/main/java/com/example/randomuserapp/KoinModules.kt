package com.example.randomuserapp

import User
import com.example.randomuserapp.remote_repo.RandomUserService
import com.example.randomuserapp.remote_repo.RandomUsersRepository
import com.example.randomuserapp.remote_repo.RandomUsersRepositoryImpl
import com.example.randomuserapp.view_model.UserListViewModel
import com.example.randomuserapp.view_model.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://randomuser.me/"

val appModule = module {

    single<RandomUsersRepository> { RandomUsersRepositoryImpl(get()) }

    single {
        Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserService::class.java)
    }

    viewModel { UserListViewModel(get()) }
    viewModel { (user: User) -> UserViewModel(user) }
}