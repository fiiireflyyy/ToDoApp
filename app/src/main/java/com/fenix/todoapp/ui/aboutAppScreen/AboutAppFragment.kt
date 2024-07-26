package com.fenix.todoapp.ui.aboutAppScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fenix.todoapp.databinding.FragmentAboutAppBinding
import com.fenix.todoapp.di.aboutAppScreen.AboutAppScreenComponent
import com.fenix.todoapp.navigation.NavManager
import com.fenix.todoapp.ui.MainActivity
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader
import javax.inject.Inject

class AboutAppFragment : Fragment() {

    private lateinit var component: AboutAppScreenComponent

    private lateinit var binding: FragmentAboutAppBinding

    @Inject
    lateinit var navManager: NavManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component = (activity as MainActivity)
            .mainActivityComponent
            .aboutAppFragmentComponent()
        component.inject(this)

        binding = FragmentAboutAppBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val divJson =(activity as MainActivity).assetReader.read("infopage.json")
        val templatesJson = divJson.optJSONObject("templates")
        val cardJson = divJson.getJSONObject("card")

        val divContext = Div2Context(
            baseContext = requireActivity(),
            configuration = createDivConfiguration(),
            lifecycleOwner = this
        )

        val divView = Div2ViewFactory(divContext, templatesJson).createView(cardJson)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        divView.layoutParams = layoutParams
        binding.root.addView(divView)
    }

    private fun createDivConfiguration() : DivConfiguration {
        val imageLoader :PicassoDivImageLoader = PicassoDivImageLoader(requireContext())
        return DivConfiguration.Builder(imageLoader)
            .visualErrorsEnabled(true)
            .actionHandler(NavigationDivActionHandler(navManager))
            .build()
    }

}