package com.downloading.githubfetch.presentation.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.downloading.githubfetch.R
import com.downloading.githubfetch.domain.model.GitRepositoryModel

class RepositoryAdapter(private var repositories: List<GitRepositoryModel>, private val onDownloadClickListener: (String, String) -> Unit) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_item, parent, false)
        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentItem = repositories[position]
        holder.repositoryNameTextView.text = currentItem.name
        holder.repositoryDescriptionTextView.text = currentItem.description

        holder.brows.setOnClickListener {
            val url = currentItem.html_url
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            }
        }

        holder.download.setOnClickListener {
            val repository = repositories[position]
            val repoName = repository.name
            val repoOwner = repository.owner.login
            onDownloadClickListener(repoName, repoOwner)
        }
    }


    override fun getItemCount() = repositories.size

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repositoryNameTextView: TextView = itemView.findViewById(R.id.repositoryNameTextView)
        val repositoryDescriptionTextView: TextView = itemView.findViewById(R.id.repositoryDescriptionTextView)
        val download: ImageView = itemView.findViewById(R.id.downLoad)
        val brows: ImageView = itemView.findViewById(R.id.browser)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRepositories: List<GitRepositoryModel>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }

}
