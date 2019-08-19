package com.example.myapplication.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Recipe::class,
            childColumns = arrayOf("title"),
            parentColumns = arrayOf("title")
        )
    ),
    primaryKeys = ["ingredient", "title"]
)
data class RecipeSearchResult(val ingredient: String, val title: String)

@Entity
data class Recipe(@PrimaryKey val title: String, val href: String, val ingredients: String, val thumbnail: String)