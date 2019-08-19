package com.example.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.Database.Recipe
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable


data class PuppyResult(val title: String, val version: Float, val href: String, val results: List<Recipe>)

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    init {
        //application.deleteDatabase("recipe.db")
        println("RecipeViewModel Created!")
    }

    fun getData(ingredient: String): Flowable<List<Recipe>> = recipeRepository.getData(ingredient)

    fun deleteRecipeItem(title: String): Disposable? = recipeRepository.deleteRecipeItem(title)

    fun fetchAndInsertItems(ingredient: String): Disposable? = recipeRepository.fetchAndInsertItems(ingredient)

    override fun onCleared() {
        super.onCleared()
        println("View Destroyed")
    }

}



