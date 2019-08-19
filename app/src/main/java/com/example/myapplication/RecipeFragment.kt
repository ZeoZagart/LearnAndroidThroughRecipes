package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Database.DBRecipe
import com.example.myapplication.View.RecipeAdapter
import com.example.myapplication.View.SwipeController
import com.example.myapplication.ViewModel.RecipeRepository
import com.example.myapplication.ViewModel.RecipeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RecipeFragment : Fragment() {
    init {
        println("fragment created")
    }

    private var getDataDisposable: Disposable? = null
    private var fetchAndInsertDisposable: Disposable? = null
    private var deleteDataDisposable: Disposable? = null
    private lateinit var viewModel: RecipeViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val recipeList = mutableListOf<DBRecipe>()
    private val adapter = RecipeAdapter(recipeList, this::createActivityIntent)
    @Inject
    lateinit var recipeRepository: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as RecipeApplication).appComponent.inject(this)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        println("OnCreateFragmentViewCalled ::: " + activity.toString())
        val callback = activity as ChangeActionBarTitle
        val fragmentView = inflater.inflate(R.layout.recipeview_fragment, container, false)


        val startIngredient = arguments?.getString(Constants.saveIngredientKey) ?: Constants.defaultIngredient
        viewModel = ViewModelProviders.of(activity!!, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RecipeViewModel(recipeRepository) as T
            }
        }).get(RecipeViewModel::class.java)

        callback.changeActionBarTitle(startIngredient)


        mRecyclerView = fragmentView.findViewById(R.id.recipe_list)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this.context)


        mSwipeRefreshLayout = fragmentView.findViewById(R.id.swipe_refresh)
        mSwipeRefreshLayout.setOnRefreshListener {
            println("swiping")
            refreshData(startIngredient)
            getData(ingredient = startIngredient)
            mSwipeRefreshLayout.isRefreshing = false
        }

        val itemTouchHelper = ItemTouchHelper(SwipeController(::deleteRecipeItem))
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

        getData(startIngredient)
        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getDataDisposable?.dispose()
        deleteDataDisposable?.dispose()
        fetchAndInsertDisposable?.dispose()
    }

    private fun deleteRecipeItem(title: String) {
        deleteDataDisposable = viewModel.deleteRecipeItem(title)
    }

    private fun refreshData(ingredient: String = Constants.defaultIngredient) {
        fetchAndInsertDisposable = viewModel.fetchAndInsertItems(ingredient)
    }

    private fun getData(ingredient: String = Constants.defaultIngredient) {
        val flowableRecipeList = viewModel.getData(ingredient)

        getDataDisposable =
            flowableRecipeList.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { newList ->
                    if (newList.isNullOrEmpty()) {
                        refreshData(ingredient)
                    }
                    val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(DiffRecipeLists(newList, recipeList))
                    recipeList.clear()
                    recipeList.addAll(newList)
                    diffResult.dispatchUpdatesTo(adapter)
                },
                {
                    println("Error in fetching data for ingredient: $ingredient" + it.message.toString())
                    onFailureCallback()
                }
            )
    }

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


    class DiffRecipeLists(private val newList: List<DBRecipe>, private val oldList: List<DBRecipe>) :
        DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].title == newList[newItemPosition].title

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

}



