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
import com.dicoding.aetherized.aetherizedgitdeck.adapter.following.FollowingAdapter
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var view: View
    private val followingViewModel by viewModels<FollowingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view = inflater.inflate(R.layout.fragment_following, container, false)

        progressBar = view.findViewById(R.id.progressBar)

        followingAdapter = FollowingAdapter(emptyList())

        recyclerView = view.findViewById(R.id.rvFollowing)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val username = arguments?.getString("username") ?: ""

        followingViewModel.initializer(username)

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followingViewModel.following.observe(viewLifecycleOwner) { following ->
            followingAdapter = FollowingAdapter(following ?: emptyList())
            recyclerView.adapter = followingAdapter
        }


        return view
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}