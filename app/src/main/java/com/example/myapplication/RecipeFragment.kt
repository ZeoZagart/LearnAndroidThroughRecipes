package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.View.RecipeAdapter
import com.example.myapplication.View.SwipeController
import com.example.myapplication.ViewModel.RecipeViewModel


class RecipeFragment : Fragment() {
    init {
        println("fragment created")
    }
    private lateinit var viewModel: RecipeViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        println("OnCreateFragmentViewCalled ::: " + activity.toString())
        val callback = activity as ChangeActionBarTitle
        val fragmentView = inflater.inflate(R.layout.recipeview_fragment, container, false)


        val startIngredient = arguments?.getString(Constants.saveIngredientKey) ?: Constants.defaultIngredient
        viewModel = ViewModelProviders.of(activity!!).get(RecipeViewModel::class.java)
        callback.changeActionBarTitle(startIngredient)


        mRecyclerView = fragmentView.findViewById(R.id.recipe_list)
        mRecyclerView.adapter = RecipeAdapter(viewModel.recipeList, ::createActivityIntent)
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)


        mSwipeRefreshLayout = fragmentView.findViewById(R.id.swipe_refresh)
        mSwipeRefreshLayout.setOnRefreshListener {
            println("swiping")
            getData(ingredient = startIngredient, networkRefresh = true)
            mSwipeRefreshLayout.isRefreshing = false
        }

        val itemTouchhelper = ItemTouchHelper(SwipeController(::deleteRecipeItem))
        itemTouchhelper.attachToRecyclerView(mRecyclerView)

        getData(startIngredient)
        return fragmentView
    }


    private fun deleteRecipeItem(ingredient: String, listPosition: Int) {
        viewModel.deleteRecipeItem(mRecyclerView.adapter!!::notifyDataSetChanged, ingredient, listPosition)
    }

    private fun getData(ingredient: String = Constants.defaultIngredient, networkRefresh: Boolean = false) =
        viewModel.fetchData(
            mRecyclerView.adapter!!::notifyDataSetChanged,
            ::onFailureCallback,
            ingredient,
            networkRefresh
        )

    private fun onFailureCallback() =
        Toast.makeText(this.context, "No Recipe Found. Please try another ingredient.", Toast.LENGTH_SHORT).show()

    private fun createActivityIntent(ingredient: String = Constants.defaultIngredient) {
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



