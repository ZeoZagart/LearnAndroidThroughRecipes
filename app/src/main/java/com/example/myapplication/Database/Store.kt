package com.example.myapplication.Database

import io.reactivex.Completable
import io.reactivex.Flowable

interface Store {
    fun put(ingredient: String, recipeList: List<Recipe>): Completable
    fun get(ingredient: String): Flowable<List<Recipe>>
    fun delete(title: String): Completable
}