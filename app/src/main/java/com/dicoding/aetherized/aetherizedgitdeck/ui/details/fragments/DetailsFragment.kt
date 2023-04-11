package com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.aetherized.aetherizedgitdeck.ui.main.MainActivity
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.Constants
import com.dicoding.aetherized.aetherizedgitdeck.pageradapter.details.DetailsPagerAdapter
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.helper.ViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.DetailsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsFragment : Fragment() {
    private lateinit var login: String
    private lateinit var view: View
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var favoriteButton: Button
    private lateinit var user: User

    private lateinit var detailsViewModel: DetailsViewModel

    private var msg = "NONE"

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab3,
            R.string.tab0,
            R.string.tab1
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view =  inflater.inflate(R.layout.fragment_details, container, false)

        @Suppress("DEPRECATION")
        user = arguments?.getParcelable(Constants.PARCELABLE_KEY)!!

        login = user.username

        detailsViewModel = obtainViewModel(this@DetailsFragment)


        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.replaceToobar(activity.toolbarTitleDetails)
        }
        findView()

        val detailsPagerAdapter = DetailsPagerAdapter(requireActivity())
        detailsPagerAdapter.username = login

        viewPager.adapter = detailsPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailsViewModel.initializer(login)

        detailsViewModel.user.observe(viewLifecycleOwner) { userDetails ->
            bindUser(userDetails!!)
        }

        detailsViewModel.isInFavorites.observe(viewLifecycleOwner) { inFavorite ->
            toggleFavorites(inFavorite)
            showToast(inFavorite)
        }

        detailsViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        favoriteButton.setOnClickListener { detailsViewModel.toggleFavorites() }

        return view
    }

    private fun bindUser(userDetails: User){
        view.findViewById<TextView>(R.id.id_tv).text = userDetails.id.toString()
        view.findViewById<TextView>(R.id.username_tv).text = userDetails.username
        view.findViewById<TextView>(R.id.name_tv).text = userDetails.name
        view.findViewById<TextView>(R.id.followers_tv).text = userDetails.followers.toString()
        view.findViewById<TextView>(R.id.following_tv).text = userDetails.following.toString()
        Glide.with(this).load(userDetails.avatarUrl).into(view.findViewById(R.id.user_avatar_iv))
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toggleFavorites(isFavorite: Boolean){
        val drawableRes: Int
        val textRes: String
        if (isFavorite)
        {
            drawableRes = R.drawable.ic_favorite_true
            textRes = getString(R.string.inFavorites)
        } else {
            drawableRes = R.drawable.ic_favorite_false
            textRes = getString(R.string.addFavorites)
        }
        favoriteButton.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
        favoriteButton.text = textRes
    }

    private fun obtainViewModel(fragment: Fragment): DetailsViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[DetailsViewModel::class.java]
    }

    private fun findView(){
        progressBar = view.findViewById(R.id.progressBar)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        favoriteButton = view.findViewById(R.id.favorite_button)
    }

    private fun showToast(inFavorite: Boolean){
        val status = if (inFavorite) "User is in Favorites" else  "User is not in Favorites"
        Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
    }

}