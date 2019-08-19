package com.example.myapplication.di

import com.example.myapplication.RecipeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DatabaseModule::class, ContextModule::class])
interface AppComponent {

    fun inject(recipeFragment: RecipeFragment)
}