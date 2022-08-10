package keren.bestcookingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import keren.bestcookingapp.bindingadapters.RecipesRowBinding
import keren.bestcookingapp.databinding.RecipesRowLayoutBinding
import keren.bestcookingapp.models.FoodRecipe
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<FoodResult>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(result: FoodResult){
            with(binding){
                RecipesRowBinding.loadImageFromUrl(recipeImageView, result.image)
                titleTextView.text = result.title
                descriptionTextView.text = result.summary
                RecipesRowBinding.setNumberOfLikes(heartTextView, result.aggregateLikes)
                RecipesRowBinding.setNumberOfMinutes(clockTextView, result.readyInMinutes)
                RecipesRowBinding.applyVeganColor(leafImageView, result.vegan)
                RecipesRowBinding.applyVeganColor(leafTextView, result.vegan)
                RecipesRowBinding.onRecipeClickListener(recipesRowLayout, result)

            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }
    // create a view and return it
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }
    //
    //associates data with the view holder at the specified position in the RecyclerView.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    //returns the total number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe){
        val recipesDiffUtil =
                RecipesDiffUtil(recipes, newData.foodResults)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.foodResults
        diffUtilResult.dispatchUpdatesTo(this)
    }
}