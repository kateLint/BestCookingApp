/*
package keren.bestcookingapp.data.database

import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.models.FoodRecipe
import keren.bestcookingapp.models.FoodResult




class RecipesConverters {
    @TypeConverters
    fun foodJokeToString(result: FoodJoke): String{
        return result.text
    }

    @TypeConverters
    fun stringToFoodJoke(data: String): FoodJoke {
        return FoodJoke(data)
    }

  //  var gson = Gson()

 */
/*   @TypeConverters
    fun foodRecipeToString(foodRecipe: FoodRecipe): String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverters
    fun stringToFoodRecipe(data: String): FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverters
    fun resultToString(result: FoodResult): String{
        return gson.toJson(result)
    }

    @TypeConverters
    fun stringToResult(data: String): FoodResult {
        val listType = object : TypeToken<FoodResult>() {}.type
        return gson.fromJson(data, listType)
    }*//*



}*/
