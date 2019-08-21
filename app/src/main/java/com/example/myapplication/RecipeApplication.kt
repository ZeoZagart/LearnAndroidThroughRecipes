package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.*

class RecipeApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        deleteDatabase("recipe.db")
        Constants.dp = resources.displayMetrics.densityDpi
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this.applicationContext))
            .netModule(NetModule())
            .databaseModule(DatabaseModule())
            .build()
    }
}