package com.downloading.githubfetch.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.downloading.githubfetch.R
import com.downloading.githubfetch.presentation.adapters.DownRepoAdapter
import com.downloading.githubfetch.presentation.viewmodels.DownloadedVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class DownloadedFragment : Fragment() {

    private lateinit var repositoryAdapter: DownRepoAdapter
    private val viewModel: DownloadedVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_downloaded, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        repositoryAdapter = DownRepoAdapter(emptyList())
        recyclerView.adapter = repositoryAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            repositoryAdapter.setRepositories(repositories)
        }
        return view
    }
}