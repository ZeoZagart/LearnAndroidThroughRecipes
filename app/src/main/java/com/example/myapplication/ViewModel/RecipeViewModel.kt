package com.example.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.Database.DBRecipe
import com.example.myapplication.Database.DatabaseStore
import com.example.myapplication.Network.PuppyService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


data class PuppyResult(val title: String, val version: Float, val href: String, val results: List<DBRecipe>)

class RecipeViewModel(val recipeStore: DatabaseStore, val puppyServe: PuppyService) : ViewModel() {
    init {
        //application.deleteDatabase("recipe.db")
        println("RecipeViewModel Created!")
    }


    //private val recipeStore = DatabaseStore(RecipeDatabase.getDatabase(application.applicationContext).DBFunctions())
    //private val puppyServe: PuppyService = Networking.create().create(PuppyService::class.java)


    fun getData(ingredient: String, networkRefresh: Boolean): Flowable<List<DBRecipe>> = recipeStore.get(ingredient)

    fun deleteRecipeItem(title: String): Disposable? =
        recipeStore.delete(title).doOnComplete { println("Recipe $title Deleted") }.subscribe()

    fun fetchAndInsertItems(ingredient: String): Disposable? {
        return puppyServe.getRecipeList(ingredient).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
            { result: PuppyResult -> recipeStore.put(ingredient, result.results).subscribe() },
            { println("Error fetching data from the internet : " + it.message.toString()) }
        )
    }



    override fun onCleared() {
        super.onCleared()
        println("View Destroyed")
    }

}



