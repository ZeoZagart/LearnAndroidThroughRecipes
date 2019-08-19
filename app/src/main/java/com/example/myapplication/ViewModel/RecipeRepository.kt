package com.example.myapplication.ViewModel

import com.example.myapplication.Database.DatabaseStore
import com.example.myapplication.Database.Recipe
import com.example.myapplication.Network.PuppyService
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeStore: DatabaseStore,
    private val puppyServe: PuppyService
) {
    fun getData(ingredient: String): Flowable<List<Recipe>> = recipeStore.get(ingredient)

    fun deleteRecipeItem(title: String): Disposable =
        recipeStore.delete(title).doOnComplete { println("Recipe $title Deleted") }.subscribe()

    fun fetchAndInsertItems(ingredient: String): Disposable {
        return puppyServe.getRecipeList(ingredient)
            .flatMapCompletable { recipeStore.put(ingredient, it.results) }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { println("Successfully fetched data") },
                { println("Error fetching data from the internet : " + it.message.toString()) }
            )
    }
}
