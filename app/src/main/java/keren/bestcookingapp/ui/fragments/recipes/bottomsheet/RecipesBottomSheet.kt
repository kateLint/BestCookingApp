package keren.bestcookingapp.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import keren.bestcookingapp.R
import keren.bestcookingapp.ui.fragments.recipes.bottomsheet.RecipesBottomSheetDirections
import keren.bestcookingapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import keren.bestcookingapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import keren.bestcookingapp.viewmodels.RecipesViewModel
import java.lang.Exception
import java.util.*

class RecipesBottomSheet: BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container,false)
        val mealTypeChipGroup = mView.findViewById<ChipGroup>(R.id.mealType_chipGroup)
        val dietTypeChipGroup = mView.findViewById<ChipGroup>(R.id.dietType_chipGroup)

/*        recipesViewModel.readMealAndDietType.asLiveData().observe(
            viewLifecycleOwner,{value ->
                mealTypeChip = value.selectedMealType
                dietTypeChip = value.selectedDietType
                updateChip(value.selectedMealTypeId, mealTypeChipGroup)
                updateChip(value.selectedDietTypeId,dietTypeChipGroup)
            }
        )*/


        dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId

        }

        mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedDietType
            mealTypeChipId = selectedChipId
        }
        mView.findViewById<Button>(R.id.apply_btn).setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
                    findNavController().navigate(action)


        }
        return mView
    }

    private fun updateChip(chupId: Int, chipGroup: ChipGroup){
        if(chupId != 0){
            try {
                chipGroup.findViewById<Chip>(chupId).isChecked = true
            }catch (e: Exception){}
        }
    }
}