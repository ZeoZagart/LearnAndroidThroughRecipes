package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ChangeActionBarTitle {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startIngredient = intent?.getStringExtra(Constants.saveIngredientKey) ?: Constants.defaultIngredient
        val bundle = Bundle()
        bundle.putString(Constants.saveIngredientKey, startIngredient)

        val recipeFragment = RecipeFragment()
        recipeFragment.arguments = bundle

        supportActionBar?.title = Constants.defaultIngredient

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, recipeFragment)
            .commit()
    }

    override fun changeActionBarTitle(newTitle: String) {
        supportActionBar!!.title = newTitle
    }

}

interface ChangeActionBarTitle {
    fun changeActionBarTitle(newTitle: String)
}


//startIngredient,this::createActivityIntent