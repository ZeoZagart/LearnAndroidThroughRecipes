package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecipeFragment : Fragment() {
    private lateinit var viewModel: RecipeViewModel
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val callback = activity as ChangeActionBarTitle


        val fragmentView = inflater.inflate(R.layout.recipeview_fragment, container, false)
        val startIngredient = arguments?.getString(Constants.saveIngredientKey) ?: Constants.defaultIngredient
        viewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)
        callback.changeActionBarTitle(startIngredient)

        mRecyclerView = fragmentView.findViewById(R.id.recipe_list)
        mRecyclerView.adapter = RecipeAdapter(viewModel.recipeList, ::createActivityIntent)
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)


        getData(startIngredient)
        return fragmentView
    }


    private fun getData(ingredient: String = Constants.defaultIngredient) =
        viewModel.fetchData(mRecyclerView.adapter!!::notifyDataSetChanged, ::onFailureCallback, ingredient)

    private fun onFailureCallback() = Toast.makeText(this.context, "No Recipe Found", Toast.LENGTH_SHORT).show()

    fun createActivityIntent(ingredient: String = Constants.defaultIngredient) {
        val bundle = Bundle()
        bundle.putString(Constants.saveIngredientKey, ingredient)

        val recipeFragment = RecipeFragment()
        recipeFragment.arguments = bundle

        fragmentManager!!.beginTransaction()
            .replace(R.id.fragment_container, recipeFragment)
            .addToBackStack(null)
            .commit()
    }

}



