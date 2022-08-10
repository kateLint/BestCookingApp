package keren.bestcookingapp.util

import androidx.recyclerview.widget.DiffUtil


class RecipesDiffUtil<T>(
        private val oldList: List<T>,
        private val newList: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
    //in this function we need to determine what is the distinguishing indicator
    //between one item and another, here I use the repoName field as an indicator
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }
    //
    //in this method we determine the indicator to be used
    //to determine whether our item changed or not
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}