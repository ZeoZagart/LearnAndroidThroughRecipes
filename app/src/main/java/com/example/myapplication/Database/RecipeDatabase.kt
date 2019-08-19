package com.example.myapplication.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBRecipe::class, DBRecipeSearchResult::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}