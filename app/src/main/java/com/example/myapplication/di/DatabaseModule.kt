package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.Database.RecipeDao
import com.example.myapplication.Database.RecipeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseFunctions(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }
}