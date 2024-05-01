package com.downloading.githubfetch.presentation.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.downloading.githubfetch.R
import com.downloading.githubfetch.presentation.adapters.RepositoryAdapter
import com.downloading.githubfetch.presentation.viewmodels.SearchVM
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: android.widget.SearchView
    private lateinit var adapter: RepositoryAdapter
    private val viewModel: SearchVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(context as Activity)
        adapter = RepositoryAdapter(emptyList()) { repoName, repoOwner ->
            lifecycleScope.launch {
                viewModel.selectBranchAndDownload(repoOwner, repoName)
            }
        }
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchRepositories(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            adapter.updateData(repositories)
        }

        viewModel.showToast.observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context as Activity, message, Toast.LENGTH_SHORT).show()
    }

}