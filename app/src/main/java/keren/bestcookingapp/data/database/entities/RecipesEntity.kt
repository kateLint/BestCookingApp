package keren.bestcookingapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity (
    @Embedded
    var foodRecipe: FoodResult
        ){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

//var foodRecipe: FoodRecipe