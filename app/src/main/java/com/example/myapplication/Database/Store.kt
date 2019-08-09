package com.example.myapplication

import com.example.myapplication.Database.DBRecipe

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>)
    fun get(ingredient: String, callback: (List<DBRecipe>?) -> Unit)
}