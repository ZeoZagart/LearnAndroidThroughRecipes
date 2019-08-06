package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecipeAdapter(
    private val recipeList: List<DBRecipe>,
    val clickListener: (String) -> Unit
) : RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mItemView = inflater.inflate(R.layout.recipe_item_view, parent, false)
        return RecipeViewHolder(mItemView)
    }

    override fun getItemCount(): Int {
        return recipeList.size // 10
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.mItemView.text = recipeList[position].title.trim()
        holder.mItemView.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(recipeList[position].href)
            startActivity(holder.itemView.context, i, null)
        }
        val ingredientList: List<String> = recipeList[position].ingredients!!.split(",")

        val firstIngredient = ingredientList[0]
        holder.mIngredientView.text =
            recipeList[position].ingredients!!.trim()//ingredientList.joinToString(separator = " .. ")


        holder.mIngredientView.setOnClickListener {
            clickListener(firstIngredient)
        }
        holder.mImageView.setOnClickListener {
            clickListener(firstIngredient)
        }


        Picasso.get().load(recipeList[position].thumbnail).resize(160, 160).into(holder.mImageView)

    }

}

class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mItemView: TextView = view.findViewById(R.id.recipe_item)
    val mIngredientView: TextView = view.findViewById(R.id.recipe_ingredients)
    val mImageView: ImageView = view.findViewById(R.id.recipe_image)
}

