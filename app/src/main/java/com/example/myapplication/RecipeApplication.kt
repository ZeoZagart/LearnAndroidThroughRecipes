package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.di.DatabaseModule
import com.example.myapplication.di.NetModule

class RecipeApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .netModule(NetModule())
        .databaseModule(DatabaseModule(this))
        .build()
}