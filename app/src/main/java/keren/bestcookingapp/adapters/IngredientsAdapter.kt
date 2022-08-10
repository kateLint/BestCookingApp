package keren.bestcookingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import keren.bestcookingapp.R
import keren.bestcookingapp.models.ExtendedIngredient
import keren.bestcookingapp.util.Constants.Companion.BASE_IMG_URL
import keren.bestcookingapp.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>()  {
    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var ingredient_imageView: ImageView
        lateinit var ingredient_name: TextView
        lateinit var ingredient_amount: TextView
        lateinit var ingredient_unit: TextView
        lateinit var ingredient_consistency: TextView
        lateinit var ingredient_original: TextView

        init {
          initializeViews()
        }
        fun initializeViews(){
            ingredient_imageView = itemView.findViewById<ImageView>(R.id.ingredient_imageView)
            ingredient_name = itemView.findViewById<TextView>(R.id.ingredient_name)
            ingredient_amount = itemView.findViewById<TextView>(R.id.ingredient_amount)
            ingredient_unit = itemView.findViewById<TextView>(R.id.ingredient_unit)
            ingredient_consistency = itemView.findViewById<TextView>(R.id.ingredient_consistency)
            ingredient_original = itemView.findViewById<TextView>(R.id.ingredient_original)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ingredient_imageView.load(BASE_IMG_URL + ingredientsList[position].image){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.ingredient_name.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        holder.ingredient_amount.text = ingredientsList[position].amount
        holder.ingredient_unit.text = ingredientsList[position].unit
        holder.ingredient_consistency.text = ingredientsList[position].consistency
        holder.ingredient_original.text = ingredientsList[position].original

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>){
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}