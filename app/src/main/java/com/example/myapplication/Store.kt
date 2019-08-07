package com.example.myapplication

import javax.security.auth.callback.Callback

interface Store {
    fun put(ingredient: String, recipeList: List<DBRecipe>)
    fun get(ingredient: String,callback: (List<DBRecipe>?)->Unit)
}