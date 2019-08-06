package com.example.myapplication

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>)
    fun get(ingredient: String): List<DBRecipe>?
}