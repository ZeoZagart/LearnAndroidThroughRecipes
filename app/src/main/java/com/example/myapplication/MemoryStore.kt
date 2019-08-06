package com.example.myapplication

class MemoryStore : Store {
    private val recipeDict = HashMap<String, List<DBRecipe>>()

    override fun put(ingredient: String, recipeList: List<DBRecipe>): Unit {
        recipeDict[ingredient] = recipeList
    }

    override fun get(ingredient: String): List<DBRecipe>? {
        return when (recipeDict.containsKey(ingredient)) {
            true -> recipeDict[ingredient]
            false -> null
        }
    }
}