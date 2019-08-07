package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface DBFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(dbRecipeSearchResults: List<DBRecipeSearchResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipeList: List<DBRecipe>)

    @Query("SELECT DBRecipe.title,href,ingredients,thumbnail FROM DBRecipeSearchResult INNER JOIN DBRecipe ON DBRecipe.title = DBRecipeSearchResult.title WHERE ingredient IS :ingredient")
    fun fetchRecipeNameList(ingredient: String): Single<List<DBRecipe>>
}