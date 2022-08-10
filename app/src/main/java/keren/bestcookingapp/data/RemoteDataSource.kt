package keren.bestcookingapp.data

import keren.bestcookingapp.data.network.FoodRecipesApi
import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.models.FoodRecipe
import keren.bestcookingapp.util.Constants
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }

    suspend fun getRandomRecipes(): Response<FoodRecipe> {
        return foodRecipesApi.getRandomRecipes(Constants.API_KEY, "5", "desert")
    }
}