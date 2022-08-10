package keren.bestcookingapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import keren.bestcookingapp.util.Constants
import keren.bestcookingapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import keren.bestcookingapp.util.Constants.Companion.QUERY_API_KEY
import keren.bestcookingapp.util.Constants.Companion.QUERY_DIET
import keren.bestcookingapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import keren.bestcookingapp.util.Constants.Companion.QUERY_NUMBER
import keren.bestcookingapp.util.Constants.Companion.QUERY_SEARCH
import keren.bestcookingapp.util.Constants.Companion.QUERY_TYPE


class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    var networkStatus: Boolean? = null
    val queries: LinkedHashMap<String, String> = LinkedHashMap()

    fun applyQueries(): Map<String, String> {
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {

    }

    fun applySearchQuery(searchQuery: String): LinkedHashMap<String, String> {

        queries.clear()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = "10"
        queries[QUERY_API_KEY] = Constants.API_KEY



        return queries


       // return searchQuery+QUERY_NUMBER+10
    }
    val savedMealType: LinkedHashMap<String, String> = LinkedHashMap()
    fun saveMealAndDietType(
        mealTypeChip: String,
        mealTypeChipId: Int,
        dietTypeChip: String,
        dietTypeChipId: Int
    ) {
        queries.clear()
        queries[QUERY_NUMBER] = "10"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = mealTypeChip
        queries[QUERY_DIET] = dietTypeChip
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"


        savedMealType[mealTypeChip] = mealTypeChip
        savedMealType[mealTypeChipId.toString()] = mealTypeChipId.toString()
        savedMealType[dietTypeChip] = dietTypeChip
        savedMealType[dietTypeChipId.toString()] = dietTypeChipId.toString()

    }


}