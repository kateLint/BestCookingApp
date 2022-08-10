package keren.bestcookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import keren.bestcookingapp.adapters.PagerAdapter
import keren.bestcookingapp.data.database.entities.FavoritesEntity
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.ui.fragments.ingredients.IngredientsFragment
import keren.bestcookingapp.ui.fragments.instructions.InstructionsFragment
import keren.bestcookingapp.ui.fragments.overview.OverviewFragment
import keren.bestcookingapp.util.Constants.Companion.RECIPE_RESULT_KEY
import keren.bestcookingapp.viewmodels.MainViewModel

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsLayout: ConstraintLayout
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var toolBar = findViewById<Toolbar>(R.id.toolBar)
        detailsLayout = findViewById<ConstraintLayout>(R.id.detailsLayout)
       // setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY,args.result)


        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        var viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId ==  R.id.save_to_favorites_menu && !recipeSaved){
            saveToFavorites(item)
        }else if(item.itemId == R.id.save_to_favorites_menu && recipeSaved){
            removeFromFavorites(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem){

        if(mainViewModel.checkInFavorites(args.result)){
            changeMenuItemColor(menuItem, R.color.white)
            recipeSaved = true
        }else{
            changeMenuItemColor(menuItem, R.color.black)
            recipeSaved = false
        }

        mainViewModel.readFavoriteRecipes.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    var recipId = args.result.recipeId
                    if (savedRecipe.id == recipId) {
                        changeMenuItemColor(menuItem, R.color.white)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun saveToFavorites(item: MenuItem){
        args.result.isSelectedToFavorites  = true
        savedRecipeId = args.result.recipeId
        val favoritesEntity =
            FavoritesEntity(args.result.recipeId, args.result)
        mainViewModel.insertFavoriteRecipes(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        recipeSaved = true
    }


    private fun removeFromFavorites(item: MenuItem){
        args.result.isSelectedToFavorites = false

        val favoritesEntity =
            FavoritesEntity(args.result.recipeId, args.result)
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.black)
        showSnackBar("Remove From Favorites")
        recipeSaved = false
    }
    private fun showSnackBar(message: String){

        Snackbar.make(detailsLayout,
            message,
            Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int){
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}