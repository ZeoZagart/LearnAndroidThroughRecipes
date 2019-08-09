package com.example.myapplication.Database

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DatabaseStore(private val dbFunctions: DBFunctions) : Store {

    override fun put(ingredient: String, recipeList: List<DBRecipe>): Unit {
        val observable = Observable.fromCallable {
            dbFunctions.insertRecipes(recipeList.map {
                DBRecipe(
                    it.title.trim(),
                    it.href.trim(),
                    it.ingredients.trim(),
                    it.thumbnail.trim()
                )
            })
            dbFunctions.insertSearchResults(recipeList.map {
                DBRecipeSearchResult(
                    ingredient.trim(),
                    it.title.trim()
                )
            })
        }

        val p = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { println("No On Next Needed ?? Probably " + it.toString()) },
                { println("Error Inserting data into the database : " + it.message) },
                { println("Data stored safely") }
            )
    }


    override fun get(ingredient: String, callback: (List<DBRecipe>?) -> Unit) {
        val p = dbFunctions.fetchRecipeNameList(ingredient)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callback(it)
                },
                {
                    println("Cannot return recipelist, message : " + it.message)
                }
            )
    }

    override fun delete(ingredient: String) {
        val p = Observable.fromCallable { dbFunctions.deleteRecipe(ingredient) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { println("No On Next Needed ?? Probably " + it.toString()) },
                { println("Error Deleting data from database : " + it.message) },
                { println("Data Item Deleted Successfully") }
            )
    }
}
