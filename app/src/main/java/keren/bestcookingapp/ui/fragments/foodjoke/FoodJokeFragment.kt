package keren.bestcookingapp.ui.fragments.foodjoke

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import keren.bestcookingapp.R
import keren.bestcookingapp.databinding.FragmentFoodJokeBinding
import keren.bestcookingapp.util.Constants.Companion.API_KEY
import keren.bestcookingapp.util.NetworkResult
import keren.bestcookingapp.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        requistFoodJoke()

        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                    if(response.data != null){
                        foodJoke = response.data.text
                        binding.foodJokeTextView.text = foodJoke
                    }
                }
                is NetworkResult.Error ->{
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{

                }
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requistFoodJoke() {
        mainViewModel.getFoodJokeApi(API_KEY)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_food_joke_menu){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return true
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
       /*     mainViewModel.readFoodJoke.observer(viewLifecycleOwner,{database ->
                if(!database.isNullOrEmpty()){
                    binding.foodJokeTextView.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            })*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}