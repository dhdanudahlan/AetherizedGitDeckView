package com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.adapter.favorites.FavoritesAdapter
import com.dicoding.aetherized.aetherizedgitdeck.helper.ViewModelFactory
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var favoritesViewModel: FavoritesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesViewModel = obtainViewModel(this@FavoritesFragment)
        favoritesViewModel.getAllFavorites().observe(viewLifecycleOwner) { favorites ->
            if (favorites != null) {
                favoritesAdapter.setFavorites(favorites)
            }
        }
        favoritesAdapter = FavoritesAdapter(ArrayList())
        progressBar = view.findViewById(R.id.progressBar)

        favoritesViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        recyclerView = view.findViewById(R.id.rv_users)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = favoritesAdapter


        return view
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(fragment: Fragment): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[FavoritesViewModel::class.java]
    }
}