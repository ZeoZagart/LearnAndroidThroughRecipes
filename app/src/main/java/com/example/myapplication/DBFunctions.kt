package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface DBFunctions {
    @Insert
    fun insertSearchResults(dbRecipeSearchResults: List<DBRecipeSearchResult>)

    @Insert
    fun insertRecipes(recipeList: List<DBRecipe>)

    @Query("SELECT DBRecipe.title,href,ingredients,thumbnail FROM DBRecipeSearchResult INNER JOIN DBRecipe ON DBRecipe.title = DBRecipeSearchResult.title WHERE ingredient IS :ingredient")
    fun fetchRecipeNameList(ingredient: String): Flowable<List<DBRecipe>>
}