package com.example.myapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBRecipe::class, DBRecipeSearchResult::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun DBFunctions(): DBFunctions


    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): RecipeDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}