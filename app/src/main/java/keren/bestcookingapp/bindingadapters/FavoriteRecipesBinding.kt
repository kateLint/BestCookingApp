package keren.bestcookingapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import keren.bestcookingapp.adapters.FavoriteRecipesAdapter
import keren.bestcookingapp.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {
    companion object{
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            mAdapter: FavoriteRecipesAdapter?
        ){
            if(favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView ->{
                        view.visibility = View.VISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView->{
                        view.visibility = View.VISIBLE
                    }
                }
                }else {
                when(view){
                    is ImageView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is TextView ->{
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView->{
                        view.visibility = View.INVISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }

}