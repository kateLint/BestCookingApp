package keren.bestcookingapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
data class FavoritesEntity (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @Embedded
    var result: FoodResult
)