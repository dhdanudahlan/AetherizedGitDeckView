package com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.adapter.followers.FollowersAdapter
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var view: View
    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_followers, container, false)


        progressBar = view.findViewById(R.id.progressBar)

        followersAdapter = FollowersAdapter(emptyList())

        recyclerView = view.findViewById(R.id.rvFollowers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val username = arguments?.getString("username") ?: ""

        followersViewModel.initializer(username)

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
            followersAdapter = FollowersAdapter(followers ?: emptyList())
            recyclerView.adapter = followersAdapter
        }

        return view
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}