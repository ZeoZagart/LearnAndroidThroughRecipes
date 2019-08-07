package com.example.myapplication

class MemoryStore : Store {
    private val recipeDict = HashMap<String, List<DBRecipe>>()

    override fun put(ingredient: String, recipeList: List<DBRecipe>): Unit {
        recipeDict[ingredient] = recipeList
    }

    override fun get(ingredient: String,callback: (List<DBRecipe>?)->Unit){
        when (recipeDict.containsKey(ingredient)) {
            true -> callback(recipeDict[ingredient])
            false -> callback(null as List<DBRecipe>?)
        }
    }
}