package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.Database.DBRecipe
import com.example.myapplication.Database.DatabaseStore
import com.example.myapplication.Database.RecipeDatabase
import com.example.myapplication.Network.Networking
import com.example.myapplication.Network.PuppyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


data class PuppyResult(val title: String, val version: Float, val href: String, val results: List<DBRecipe>)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    init {println("RecipeViewModel Created!")}

    val recipeList = mutableListOf<DBRecipe>()
    private val recipeStore =
        DatabaseStore(RecipeDatabase.getDatabase(application.applicationContext).DBFunctions())
    private val puppyServe: PuppyService = Networking.create().create(PuppyService::class.java)


    fun fetchData(OnSuccessCallback: () -> Unit, onFailureCallback: () -> Unit, ingredient: String = Constants.defaultIngredient) {
        val callback: (List<DBRecipe>?) -> Unit = { callbackRecipeList: List<DBRecipe>? ->
            if (callbackRecipeList.isNullOrEmpty()) {
                println("Fetching Data from Network Call")
                val p = puppyServe.getRecipeList(ingredient).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result: PuppyResult ->
                            recipeList.clear()
                            recipeList.addAll(result.results)
                            recipeStore.put(ingredient, result.results)
                            OnSuccessCallback()
                        },
                        { throwable: Throwable ->
                            println("Could not fetch any Data from API " + throwable.message)
                            onFailureCallback()
                        }
                    )
            } else {
                println("Fetched from Database : " + ingredient )
                recipeList.clear()
                recipeList.addAll(callbackRecipeList)
                OnSuccessCallback()
            }
        }


        recipeStore.get(ingredient, callback)
    }

    override fun onCleared() {
        super.onCleared()
        println("View Destroyed")
    }

}



