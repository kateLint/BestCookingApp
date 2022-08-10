package keren.bestcookingapp.models

import keren.bestcookingapp.data.database.entities.FavoritesEntity


object FavoriteRecipesManager {

    var mFavoritesEntityList: ArrayList<FavoritesEntity> = arrayListOf()


    fun setFavoritesEntity( favoritesEntityList: ArrayList<FavoritesEntity>){
        mFavoritesEntityList = favoritesEntityList
    }

    fun addFavoritesEntity( favoritesEntityList: FavoritesEntity){
        mFavoritesEntityList.add(favoritesEntityList)
    }

    fun getFavoritesEntetyList(): List<FavoritesEntity> {
        return mFavoritesEntityList
    }
}