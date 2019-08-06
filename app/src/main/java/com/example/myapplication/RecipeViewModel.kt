package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


data class PuppyResult(val title: String, val version: Float, val href: String, val results: List<DBRecipe>)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    init {
        application.deleteDatabase("recipe.db")
        println("THIS IS THE CREATION OF THE GODLY LEGENDARY RECIPE VIEW MODEL HHAHHHHAHHHAHAHHAHAHHAHAHHAHAHAHHHHAHHAHAHAHAHHAHA")
    }

    val recipeList = mutableListOf<DBRecipe>()
    //private val recipeStore = MemoryStore()
    val db = RecipeDatabase.getDatabase(application.applicationContext)
    private val recipeStore = DatabaseStore(db.DBFunctions())

    private val puppyServe: PuppyService = Networking.create().create(PuppyService::class.java)
    private val currentIngredientString = Constants.defaultIngredient


    fun fetchData(
        callback: () -> Unit,
        onFailureCallback: () -> Unit,
        ingredient: String = Constants.defaultIngredient
    ) {
        println("FETCHING DATA ::::::: ::::::: ::::::: :::::: :::::: :::::: ::::::: :::: ::::: :::::: :::::::: :::::::::")
        val tempList = recipeStore.get(ingredient)

        if (tempList.isNullOrEmpty()) {
            println("ACTUALLY FETCHING DATA ::::::: ::::::: ::::::: :::::: :::::: :::::: ::::::: :::: ::::: :::::: :::::::: :::::::::")
            puppyServe.getRecipeList(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result: PuppyResult ->
                        recipeList.clear()
                        recipeList.addAll(result.results)
                        recipeStore.put(ingredient, result.results)
                        //recipeDict[ingredient] = recipeList
                        callback()
                    },
                    { throwable: Throwable ->
                        println("Could not fetch any Data from API " + throwable.message)
                        onFailureCallback()
                    }
                )
        } else {
            recipeList.clear()
            recipeList.addAll(tempList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("View Destroyed")
    }

}



