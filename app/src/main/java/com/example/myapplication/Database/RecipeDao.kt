package com.example.myapplication.Database

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(recipeSearchResults: List<RecipeSearchResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipeList: List<Recipe>)

    @Query("DELETE FROM Recipe WHERE title = :title")
    fun deleteRecipeDBRecipe(title: String)

    @Query("DELETE FROM RecipeSearchResult WHERE title = :title")
    fun deleteRecipeDBRecipeSearchResult(title: String)

    @Transaction
    fun deleteRecipe(title: String){
        deleteRecipeDBRecipeSearchResult(title)
        deleteRecipeDBRecipe(title)
    }

    @Query("SELECT Recipe.title,href,ingredients,thumbnail FROM RecipeSearchResult INNER JOIN Recipe ON Recipe.title = RecipeSearchResult.title WHERE ingredient = :ingredient")
    fun fetchRecipeNameList(ingredient: String): Flowable<List<Recipe>>
}