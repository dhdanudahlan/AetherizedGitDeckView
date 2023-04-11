package com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.aetherized.aetherizedgitdeck.R

class MoreDetailsFragment : Fragment() {
    private lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_more_details, container, false)
        return view
    }
}