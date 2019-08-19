package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.*

class RecipeApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .netModule(NetModule())
        .databaseModule(DatabaseModule())
        .build()
}