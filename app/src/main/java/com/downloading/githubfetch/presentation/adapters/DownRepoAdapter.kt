package com.downloading.githubfetch.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.downloading.githubfetch.R
import com.downloading.githubfetch.domain.model.GitRepoDBDataClass

class DownRepoAdapter(private var repositories: List<GitRepoDBDataClass>) :
    RecyclerView.Adapter<DownRepoAdapter.RepositoryDatabaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryDatabaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_database_item, parent, false)
        return RepositoryDatabaseViewHolder(view)
    }

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: RepositoryDatabaseViewHolder, position: Int) {
        val repository = repositories[position]
        repository.let {
            holder.repositoryNameTextView.text = it.name
            holder.repositoryDescriptionTextView.text = it.owner
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRepositories(newRepositories: List<GitRepoDBDataClass>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }

    inner class RepositoryDatabaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repositoryNameTextView: TextView = itemView.findViewById(R.id.repositoryName)
        val repositoryDescriptionTextView: TextView = itemView.findViewById(R.id.repositoryOwner)
    }
}

