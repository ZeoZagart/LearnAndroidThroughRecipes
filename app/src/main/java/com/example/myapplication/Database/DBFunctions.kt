package com.example.myapplication.Database

import androidx.room.*
import io.reactivex.Single

@Dao
interface DBFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(dbRecipeSearchResults: List<DBRecipeSearchResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipeList: List<DBRecipe>)

    @Query("DELETE FROM DBRecipe WHERE title = :title")
    fun deleteRecipeDBRecipe(title: String)

    @Query("DELETE FROM DBRecipeSearchResult WHERE title = :title")
    fun deleteRecipeDBRecipeSearchResult(title: String)

    @Transaction
    fun deleteRecipe(title: String){
        deleteRecipeDBRecipeSearchResult(title)
        deleteRecipeDBRecipe(title)
    }

    @Query("SELECT DBRecipe.title,href,ingredients,thumbnail FROM DBRecipeSearchResult INNER JOIN DBRecipe ON DBRecipe.title = DBRecipeSearchResult.title WHERE ingredient = :ingredient")
    fun fetchRecipeNameList(ingredient: String): Single<List<DBRecipe>>
}