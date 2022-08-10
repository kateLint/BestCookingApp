package keren.bestcookingapp.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import keren.bestcookingapp.R
import keren.bestcookingapp.adapters.FavoriteRecipesAdapter
import keren.bestcookingapp.databinding.FragmentFavoriteRecipesBinding
import keren.bestcookingapp.models.FavoriteRecipesManager
import keren.bestcookingapp.ui.gone
import keren.bestcookingapp.ui.visible
import keren.bestcookingapp.viewmodels.MainViewModel


@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()


    private val mAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(requireActivity(), mainViewModel)
    }

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        setupRecyclerView(binding.favoriteRecipesRecycleView)


        checkAdapter()

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner) { favoritesList ->

            mAdapter.setData(favoritesList)
            mAdapter.notifyDataSetChanged()

        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipes()
            mAdapter.setData(FavoriteRecipesManager.getFavoritesEntetyList())
            mAdapter.notifyDataSetChanged()
            showSnackBar()
        }
        return true
    }

    private fun setupRecyclerView(recycleView: RecyclerView){
        recycleView.adapter = mAdapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showSnackBar(){
        Snackbar.make(binding.root,
        "All recipes removed.",
        Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

    override fun onResume() {
        super.onResume()
        checkAdapter()
    }

    private fun checkAdapter(){
        if(FavoriteRecipesManager.getFavoritesEntetyList().isEmpty()){
            binding.favoriteRecipesRecycleView.gone()
            binding.emptyLLView.visible()
        }else{
            binding.favoriteRecipesRecycleView.visible()
            binding.emptyLLView.gone()
        }
        mAdapter.setData(FavoriteRecipesManager.getFavoritesEntetyList())
        mAdapter.notifyDataSetChanged()
    }

}