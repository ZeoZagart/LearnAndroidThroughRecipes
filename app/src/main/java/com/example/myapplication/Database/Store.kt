package com.example.myapplication.Database

import io.reactivex.Completable
import io.reactivex.Flowable

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>): Completable
    fun get(ingredient: String): Flowable<List<DBRecipe>>
    fun delete(title: String): Completable
}