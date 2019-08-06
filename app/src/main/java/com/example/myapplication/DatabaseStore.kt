package com.example.myapplication


class DatabaseStore(private val dbFunctions: DBFunctions) : Store {

    override fun put(ingredient: String, recipeList: List<DBRecipe>): Unit {
        dbFunctions.insertSearchResults(recipeList.map { DBRecipeSearchResult(ingredient, it.title) })
        dbFunctions.insertRecipes(recipeList)
    }

    override fun get(ingredient: String): List<DBRecipe>? {
        val p = dbFunctions.fetchRecipeNameList(ingredient).blockingFirst()
        return when (p.isNullOrEmpty()) {
            true -> null
            false -> p
        }
    }
}
//
//        val recipeFlowable = dbFunctions.fetchRecipeNameList(ingredient)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(
//                                {dbrecipeList:List<DBRecipe> ->
//
//                                },
//                                {
//
//                                }
//                            )


//
//puppyServe.getRecipeList(ingredient)
//.subscribeOn(Schedulers.io())
//.observeOn(AndroidSchedulers.mainThread())
//.subscribe(
//{ result: PuppyResult ->
//    recipeList.clear()
//    recipeList.addAll(result.results)
//    recipeStore.put(ingredient,result.results)
//    //recipeDict[ingredient] = recipeList
//    callback()
//},
//{ throwable: Throwable ->
//    println("Could not fetch any Data from API " + throwable.message)
//    onFailureCallback()
//}
//)