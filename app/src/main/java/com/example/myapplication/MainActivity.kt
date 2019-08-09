package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ChangeActionBarTitle {
    override fun onCreate(savedInstanceState: Bundle?) {
        println("OnCreateActivityCalled")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val startIngredient = intent?.getStringExtra(Constants.saveIngredientKey) ?: Constants.defaultIngredient
        val bundle = Bundle()
        bundle.putString(Constants.saveIngredientKey, startIngredient)


        var recipeFragment = supportFragmentManager.findFragmentByTag("recipe")
        if (recipeFragment == null || recipeFragment !is RecipeFragment) {
            recipeFragment = RecipeFragment()
            recipeFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, recipeFragment, "recipe")
                .commit()
        }

        supportActionBar?.title = Constants.defaultIngredient

    }

    override fun changeActionBarTitle(newTitle: String) {
        supportActionBar!!.title = newTitle
    }


}

interface ChangeActionBarTitle {
    fun changeActionBarTitle(newTitle: String)
}

