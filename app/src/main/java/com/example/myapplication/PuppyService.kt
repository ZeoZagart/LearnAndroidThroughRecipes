package com.example.myapplication

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PuppyService {
    @GET("/api")
    fun getRecipeList(@Query("i") ingredient: String): Single<PuppyResult>
}