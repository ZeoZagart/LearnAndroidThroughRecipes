package com.example.myapplication.Database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DatabaseStore @Inject constructor(private val recipeDao: RecipeDao) : Store {
    override fun put(ingredient: String, recipeList: List<Recipe>): Completable {
        return Completable.fromAction {
            // insert recipe
            recipeDao.insertRecipes(recipeList.map {
                Recipe(
                    it.title.trim(),
                    it.href.trim(),
                    it.ingredients.trim(),
                    it.thumbnail.trim()
                )
            })
            // insert search result
            recipeDao.insertSearchResults(recipeList.map {
                RecipeSearchResult(
                    ingredient.trim(),
                    it.title.trim()
                )
            })
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }


    override fun get(ingredient: String): Flowable<List<Recipe>> {
        return recipeDao.fetchRecipeNameList(ingredient)
    }

    override fun delete(title: String): Completable {
        return Completable.fromAction { recipeDao.deleteRecipe(title) }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
