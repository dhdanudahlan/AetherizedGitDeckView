package com.dicoding.aetherized.aetherizedgitdeck.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.adapter.browse.UserAdapter
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiConfig
import com.dicoding.aetherized.aetherizedgitdeck.data.remote.api.ApiService
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.viewmodel.BrowseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BrowseFragment : Fragment() {
    private lateinit var apiService: ApiService
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvSearchKeyword: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var view: View
    private var searchJob: Job? = null
    private val browseViewModel by viewModels<BrowseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_browse, container, false)

        apiService = ApiConfig.getApiService()

        progressBar = view.findViewById(R.id.progressBar)
        tvSearchKeyword = view.findViewById(R.id.tvSearchKeyword)
        searchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = viewLifecycleOwner.lifecycleScope.launch {
                    newText?.let {
                        delay(300)
                        browseViewModel.setKeywords(it)
                        browseViewModel.searchUsers(it)
                    }
                }
                return true
            }
        })

        recyclerView = view.findViewById(R.id.rvUsers)
        userAdapter = UserAdapter(getUserList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = userAdapter

        browseViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        browseViewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter.updateList(users)
        }

        browseViewModel.searchKeywords.observe(viewLifecycleOwner) {
            setKeywordsData(it)
        }

        return view
    }

    private fun getUserList(): List<User> {
        return browseViewModel.users.value ?: emptyList()
    }

    private fun setKeywordsData(keywords: String) {
        tvSearchKeyword.text = keywords
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
