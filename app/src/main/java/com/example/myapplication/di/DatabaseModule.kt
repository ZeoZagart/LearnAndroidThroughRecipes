package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.Database.DBFunctions
import com.example.myapplication.Database.DatabaseStore
import com.example.myapplication.Database.RecipeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(val context: Context) {
    @Provides
    @Singleton
    fun provideDatabaseFunctions(): DBFunctions {
        return RecipeDatabase.getDatabase(context).DBFunctions()
    }

    @Provides
    @Singleton
    fun provideDatabaseStore(functions: DBFunctions): DatabaseStore {
        return DatabaseStore(functions)
    }
}