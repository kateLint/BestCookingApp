package keren.bestcookingapp.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import keren.bestcookingapp.R
import keren.bestcookingapp.adapters.IngredientsAdapter
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment: Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val arg = arguments
        val myBundle: FoodResult? = arg?.getParcelable(RECIPE_RESULT_KEY)

        setupRecycleview(view)
    /*    myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }*/
        return view
    }


    private fun setupRecycleview(view: View){
        view.findViewById<RecyclerView>(R.id.ingredients_recycleview).adapter = mAdapter
        view.findViewById<RecyclerView>(R.id.ingredients_recycleview).layoutManager = LinearLayoutManager(requireContext())
    }
}