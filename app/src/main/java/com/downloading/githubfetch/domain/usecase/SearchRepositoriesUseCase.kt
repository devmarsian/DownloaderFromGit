package com.downloading.githubfetch.domain.usecase

import com.downloading.githubfetch.data.remote.GitHubService
import com.downloading.githubfetch.domain.model.Branch
import com.downloading.githubfetch.domain.model.GitRepositoryModel
import java.io.IOException

class SearchRepositoriesUseCase(private val service: GitHubService) {

    suspend fun searchRepositories(user: String): List<GitRepositoryModel> {
        val response = service.getUserRepositories(user)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw IOException("Search repositories failed with status code: ${response.code()}")
        }
    }

    suspend fun getBranches(repoOwner: String, repoName: String): List<Branch> {
        val response = service.getBranches(repoOwner, repoName)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw IOException("Fetching branches failed with status code: ${response.code()}")
        }
    }
}
