package com.example.myapplication.Database

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>)
    fun get(ingredient: String, callback: (List<DBRecipe>?) -> Unit)
    fun delete(ingredient: String)
}