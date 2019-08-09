package com.example.myapplication.Network

import com.example.myapplication.ViewModel.PuppyResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PuppyService {
    @GET("/api")
    fun getRecipeList(@Query("i") ingredient: String): Single<PuppyResult>
}