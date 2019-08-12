package com.example.myapplication.Database

import io.reactivex.Completable
import io.reactivex.Single

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>): Completable
    fun get(ingredient: String): Single<List<DBRecipe>>
    fun delete(ingredient: String): Completable
}