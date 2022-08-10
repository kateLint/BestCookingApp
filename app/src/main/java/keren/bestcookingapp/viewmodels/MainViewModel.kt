package keren.bestcookingapp.viewmodels

import android.app.Application
import android.content.Context
import android.content.Entity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.arrayMapOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import keren.bestcookingapp.data.Repository
import keren.bestcookingapp.data.database.entities.FavoritesEntity
import keren.bestcookingapp.models.FavoriteRecipesManager
import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.models.FoodRecipe
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var readRecipes: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    var readFavoriteRecipes: MutableLiveData<List<FavoritesEntity>> = MutableLiveData()
    var favoriesRecipesEntity = arrayListOf<FavoritesEntity>()

    companion object{

    }
    //var listOfFavoriteRecipes = arrayListOf<FoodResult>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun getFoodJokeApi(API_KEY: String) = viewModelScope.launch {
        getFoodJoke(API_KEY)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        val responseRemote = repository.remote.getRecipes(queries)//getRandomRecipes()
        getRecipesSafeCall(responseRemote, queries){
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getRecipesSafeCall(responseRemote: Response<FoodRecipe>  ,queries: Map<String, String>, callback: (List<FoodResult>) -> Unit) {

        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = responseRemote//repository.remote.getRandomRecipes()//getRecipes(queries) //
                recipesResponse.value = handleFoodRecipesResponse(responseRemote)

            /*    val foodRecipe = recipesResponse.value!!.data
                if(foodRecipe != null) {
                   offlineCacheRecipes(foodRecipe)
                }*/
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }


    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()?.foodResults?.isEmpty() == true -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getFoodJoke(apiKey: String) {

        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = NetworkResult.Success(response.body()!!)

                /*    val foodRecipe = recipesResponse.value!!.data
                    if(foodRecipe != null) {
                       offlineCacheRecipes(foodRecipe)
                    }*/
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun searchRecipes(applySearchQuery: LinkedHashMap<String, String>) = viewModelScope.launch {
        val responseRemote = repository.remote.searchRecipes(applySearchQuery)
            getRecipesSafeCall(responseRemote, applySearchQuery){
            }
    }

    fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        //save to db and list in app
        favoriesRecipesEntity.add(FavoritesEntity(favoritesEntity.id, favoritesEntity.result))
        FavoriteRecipesManager.setFavoritesEntity(favoriesRecipesEntity)
      //  listOfFavoriteRecipes.add(favoritesEntity.result)
        readFavoriteRecipes.value = favoriesRecipesEntity
    }


    fun deleteAllFavoriteRecipes() {
        favoriesRecipesEntity.clear()
        FavoriteRecipesManager.setFavoritesEntity(arrayListOf())
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {

        if(favoriesRecipesEntity.contains(favoritesEntity)){
            favoriesRecipesEntity.remove(favoritesEntity)
        }
        FavoriteRecipesManager.setFavoritesEntity(favoriesRecipesEntity)
        readFavoriteRecipes.value = favoriesRecipesEntity
    }


    fun getJsonDataFromAsset(context: Context, fileName: String): String?{
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }catch (ioException: IOException){
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun checkInFavorites(result: FoodResult): Boolean {
        FavoriteRecipesManager.getFavoritesEntetyList().forEach { favoritesEntity ->
            if (favoritesEntity.id == result.recipeId) return true
        }
        return false
    }
}


