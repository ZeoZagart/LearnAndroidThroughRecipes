package com.example.myapplication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DBRecipe::class,
            childColumns = arrayOf("title"),
            parentColumns = arrayOf("title")
        )
    ),
    primaryKeys = ["ingredient", "title"]
)
data class DBRecipeSearchResult(val ingredient: String, val title: String)

@Entity
data class DBRecipe(@PrimaryKey val title: String, val href: String?, val ingredients: String?, val thumbnail: String?)