package keren.bestcookingapp.ui.fragments.recipes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import keren.bestcookingapp.R
import keren.bestcookingapp.adapters.RecipesAdapter
import keren.bestcookingapp.databinding.FragmentRecipesBinding
import keren.bestcookingapp.models.FoodRecipe
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.NetworkListener
import keren.bestcookingapp.util.NetworkResult
import keren.bestcookingapp.util.observeOnce
import keren.bestcookingapp.viewmodels.MainViewModel
import keren.bestcookingapp.viewmodels.RecipesViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var networkListener: NetworkListener


    private lateinit var recycle: RecyclerView
    private lateinit var mView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)



        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider, SearchView.OnQueryTextListener {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.recipes_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    searchApiData(query)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //binding.lifecycleOwner = viewLifecycleOwner

        // Inflate the layout for this fragment
        //mView = inflater.inflate(R.layout.fragment_recipes, container, false)
        recycle = binding.recyclerview // mView.findViewById<RecyclerView>(R.id.recyclerview)

        setupRecyclerView()
    //    getJsonFromFile()
        requestApiData()


    /*    recipesViewModel.readBackOnline.observe(viewLifecycleOwner,{
            recipesViewModel.backOnline = it
        })*/

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status->
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()

                }
        }

        binding.floatingActionButton.setOnClickListener{
           if(recipesViewModel.networkStatus == true){
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            }else{

            }
        }


        return binding.root
    }



    private fun setupRecyclerView() {
        recycle.adapter = mAdapter
        recycle.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }


    private fun readDatabase(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner,{ databse->
                    requestApiData()

              /*  if(databse.isNotEmpty() && ! args.backFromBottomSheet){
                    mAdapter.setData(databse[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }*/
            })

        }
    }

    private fun getJsonFromFile(): FoodRecipe {
        val jsonFileString = context?.let { mainViewModel.getJsonDataFromAsset(it, "initSearch.json") }
        val gson = Gson()
        val listRecipes = object : TypeToken<FoodRecipe>(){}.type
        val recipes: FoodRecipe= gson.fromJson(jsonFileString, listRecipes)
        return recipes

    }

    private fun requestApiData() {
       if( recipesViewModel.queries.isNullOrEmpty()){
           recipesViewModel.applyQueries()
       }

        var foodRecipes = getJsonFromFile()
        mAdapter.setData(foodRecipes)

        /*     mainViewModel.getRecipes(recipesViewModel.queries)
             mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response->
                 when(response){
                     is NetworkResult.Success -> {
                         hideShimmerEffect()
                         response.data?.let { mAdapter.setData(it) }
                     }
                     is NetworkResult.Error -> {
                         hideShimmerEffect()
                         Toast.makeText(
                             requireContext(),
                             response.message.toString(),
                             Toast.LENGTH_SHORT
                         ).show()
                     }
                     is NetworkResult.Loading ->{
                         showShimmerEffect()
                     }
                 }
             }*/
    }

    private fun searchApiData(searchQuery: String){
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
/*        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner){response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let{ mAdapter.setData(it)}
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        }*/
    }



    private fun showShimmerEffect() {
       // mView.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
       // mView.recyclerview.hideShimmer()
    }
}