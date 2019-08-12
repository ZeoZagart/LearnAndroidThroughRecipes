package com.example.myapplication.Database

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DatabaseStore(private val dbFunctions: DBFunctions) : Store {

    override fun put(ingredient: String, recipeList: List<DBRecipe>): Completable {
        return Completable.fromAction {
            // insert recipe
            dbFunctions.insertRecipes(recipeList.map {
                DBRecipe(
                    it.title.trim(),
                    it.href.trim(),
                    it.ingredients.trim(),
                    it.thumbnail.trim()
                )
            })
            // insert search result
            dbFunctions.insertSearchResults(recipeList.map {
                DBRecipeSearchResult(
                    ingredient.trim(),
                    it.title.trim()
                )
            })
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override fun get(ingredient: String): Single<List<DBRecipe>> {
        return dbFunctions.fetchRecipeNameList(ingredient).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun delete(title: String): Completable {
        return Completable.fromAction { dbFunctions.deleteRecipe(title) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
