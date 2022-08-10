package keren.bestcookingapp.bindingadapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import keren.bestcookingapp.data.database.entities.FoodJokeEntity
import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.util.NetworkResult

class FoodJokeBinding {
    companion object{

        //readApiResponse3, readDatabase3, requireAll = false
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            when(apiResponse){
                is NetworkResult.Loading ->{
                    when(view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                        }
                    }
                }
                is NetworkResult.Error ->{
                    when(view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView ->{
                            view.visibility = View.VISIBLE
                            if(database != null){
                                if(database.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success ->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }
                        is  MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        //readApiresponse4, readDatabase4, requerAll = true
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            if(database != null){
                if (database.isEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }
    }
}