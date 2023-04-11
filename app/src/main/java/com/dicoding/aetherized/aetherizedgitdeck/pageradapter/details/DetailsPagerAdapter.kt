package com.dicoding.aetherized.aetherizedgitdeck.pageradapter.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments.FollowersFragment
import com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments.FollowingFragment
import com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments.MoreDetailsFragment

class DetailsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> MoreDetailsFragment()
            1 -> FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username) // Pass the username value to FollowersFragment using a Bundle
                }
            }
            2 -> FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username) // Pass the username value to FollowersFragment using a Bundle
                }
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}