package keren.bestcookingapp.data.network

import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    //GET https://api.spoonacular.com/recipes/complexSearch
    //https://api.spoonacular.com/recipes/complexSearch?number=50&apiKey=8dda3ad67e424e5596b731c3d285ea3f&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap number: Map<String, String>,
    ): Response<FoodRecipe>

    //  @QueryMap queries: Map<String, String>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>

    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey:String,
        @Query("number") number:String,
        @Query("tag")tags:String
    ): Response<FoodRecipe>
}