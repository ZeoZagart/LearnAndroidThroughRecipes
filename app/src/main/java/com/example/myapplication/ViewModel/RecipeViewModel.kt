package com.example.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.Database.DBRecipe
import com.example.myapplication.Database.DatabaseStore
import com.example.myapplication.Database.RecipeDatabase
import com.example.myapplication.Network.Networking
import com.example.myapplication.Network.PuppyService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


data class PuppyResult(val title: String, val version: Float, val href: String, val results: List<DBRecipe>)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    init {
        println("RecipeViewModel Created!")
    }

    val recipeList = mutableListOf<DBRecipe>()
    private val recipeStore = DatabaseStore(RecipeDatabase.getDatabase(application.applicationContext).DBFunctions())
    private val puppyServe: PuppyService = Networking.create().create(PuppyService::class.java)


    private fun fillRecipeList(newRecipeList: List<DBRecipe>) {
        recipeList.clear()
        recipeList.addAll(newRecipeList)
    }

    fun getData(ingredient: String, networkRefresh: Boolean): Single<List<DBRecipe>> {
        return recipeStore.get(ingredient).flatMap { list: List<DBRecipe> ->
            if (list.isEmpty() || networkRefresh) {
                puppyServe.getRecipeList(ingredient).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).map { result ->
                    fillRecipeList(result.results)
                    recipeStore.put(ingredient, result.results).subscribe(
                        { println("inserted data for ingredient : $ingredient in database") },
                        { println("Error inserting data on database : " + it.message.toString()) }
                    )
                    result.results
                }
            } else {
                fillRecipeList(list)
                Single.just(list)
            }
        }
    }


    fun deleteRecipeItem(OnSuccessCallback: () -> Unit, title: String, listPosition: Int) {
        val recipeDeleteCompleteable =
            recipeStore.delete(title).doOnComplete { println("Recipe $title Deleted") }.subscribe()
        recipeList.removeAt(listPosition)
        OnSuccessCallback()
    }

    override fun onCleared() {
        super.onCleared()
        println("View Destroyed")
    }

}



