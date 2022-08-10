package keren.bestcookingapp.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import keren.bestcookingapp.models.FoodJoke
import keren.bestcookingapp.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(

    tableName = FOOD_JOKE_TABLE)

data class FoodJokeEntity (
    @Embedded
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}