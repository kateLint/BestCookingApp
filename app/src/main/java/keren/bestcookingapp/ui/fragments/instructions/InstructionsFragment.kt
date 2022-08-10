package keren.bestcookingapp.ui.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import keren.bestcookingapp.R
import keren.bestcookingapp.models.FoodResult
import keren.bestcookingapp.util.Constants.Companion.RECIPE_RESULT_KEY

class InstructionsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_instructions, container,false)

        val args = arguments
        val myBundle: FoodResult? = args?.getParcelable(RECIPE_RESULT_KEY)
        var webView = view.findViewById<WebView>(R.id.instructions_webView)
        webView.webViewClient = object: WebViewClient(){

        }
        val websiteUrl: String = myBundle?.sourceUrl!!
        webView.loadUrl(websiteUrl)
        return view
    }
}